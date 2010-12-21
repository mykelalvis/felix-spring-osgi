package org.ertuo.taoplugin.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.impl.cookie.DateUtils;
import org.ertuo.taoplugin.bean.CityNum;
import org.ertuo.taoplugin.bean.PhoneNum;
import org.ertuo.taoplugin.bean.ScheduleProcess;
import org.ertuo.taoplugin.dao.GeneralDao;
import org.ertuo.taoplugin.dao.PhoneNumDao;
import org.ertuo.taoplugin.facade.TopFacade;
import org.ertuo.taoplugin.facade.XyzCodexCronService;
import org.ertuo.taoplugin.web.util.LianTingWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.taobao.api.domain.Item;

/**
 * �������
 * 
 * @author mo.duanm
 * 
 */
@Controller
public class PhoneNumcontroller {

    private static final Logger logger      = Logger.getLogger(PhoneNumcontroller.class.getName());

    final UserService           userService = UserServiceFactory.getUserService();

    @Autowired
    PhoneNumDao                 phoneNumDao;

    @Autowired
    LianTingWebClient           lianTingWebClient;

    @Autowired
    XyzCodexCronService         xyzCodexCronService;

    @Autowired
    GeneralDao<ScheduleProcess> scheduleDao;

    @Autowired
    TopFacade                   topFacade;

    @Autowired
    GeneralDao<CityNum>         cityNumDao;

    @RequestMapping(value = "/phone", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("messages-list");
        List<PhoneNum> pns = phoneNumDao.top();
        logger.info("���ݳ���" + pns.size());
        modelAndView.addObject("phoneNums", pns);
        modelAndView.setViewName("phoneNum/list");
        return modelAndView;
    }

    @RequestMapping(value = "/phone/add")
    public String add(ModelMap m, String num, String city, String date, String prise) {
        logger.info("�������" + num);
        if (StringUtils.isBlank(prise) || StringUtils.isBlank(date) || StringUtils.isBlank(city)
            || StringUtils.isBlank(num)) {
            logger.warning("����Ϊ��");
            m.put("rs", "������Ч");
            return "phoneNum/add";
        }
        PhoneNum p = new PhoneNum();
        p.setCity(city);
        p.setDate(date);
        p.setNum(num);
        p.setPrise(prise);
        phoneNumDao.createPhoneNum(p);
        m.put("rs", "���" + num + "�ɹ�");
        return "phoneNum/add";

    }

    @RequestMapping("/phone/search")
    public ModelAndView search(ModelAndView mv, String ps) {
        mv.setViewName("phoneNum/search");
        String[] totalPages = null;
        try {
            HtmlPage page = lianTingWebClient.getCurrentPage("017%7C017001", ps);
            //logger.info(page.asXml());
            // �����ҳ��
            totalPages = lianTingWebClient.getTotalPage(page);
            if (totalPages != null) {
                mv.addObject("currPage", totalPages[0]);
                mv.addObject("totalPage", totalPages[1]);
            }
            logger.info("��ҳ��" + ToStringBuilder.reflectionToString(totalPages));
            xyzCodexCronService.asynCron(lianTingWebClient.getTr(page), "�人");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            logger.info("��ѯ�������" + sw.toString());
            mv.addObject("rs", "������ͨ����");
            return mv;
        }
        mv.addObject("rs", "��ѯ��ҳ��" + totalPages);
        return mv;
    }

    @RequestMapping("/phone/cron")
    public ModelAndView cron(ModelAndView mv) {
        mv.setViewName("phoneNum/search");

        List<ScheduleProcess> sps = scheduleDao
            .find("select w from ScheduleProcess w where w.isUpdateTOP=false order by w.date desc ");
        if (sps.size() < 1) {
            logger.info("��ǰû����Ҫ����ĳ���");
            return mv;
        }

        ScheduleProcess sp = sps.get(0);//scheduleDao.get(city);
        CityNum cy = cityNumDao.get(sp.getId());
        logger.info("��ǰ���ڴ���ĳ�����" + ToStringBuilder.reflectionToString(cy));
        /*
        if (sp == null) {
            //mv.setViewName("redirect:http://taohaoma.appspot.com/app/schedule/check?city=" + city);
            this.createSchedule(city);
            return mv;
        }*/
        if ((sp.getCurrPage() - sp.getTotalPage() == 1) && !sp.isUpdateTOP()) {
            String desc = this.getTopContext(cy.getNumber());
            logger.info("saleNum list:" + desc);
            if (StringUtils.isNotBlank(desc)) {
                logger.info("��ʼ����[" + cy.getChsName() + "]����Ϊ[" + sp.getDate() + "]");
                Item it = topFacade.updateItemDesc(cy.getItemId(), desc,
                    cy.getTitle() + DateUtils.formatDate(new Date(), "MMdd"));
                if (it != null) {
                    logger.info("���½��[" + ToStringBuilder.reflectionToString(it) + "]");
                    sp.setUpdateTOP(true);
                    scheduleDao.create(sp);
                }
                mv.addObject("it", it);
            }

        }
        if (sp.getCurrPage() <= sp.getTotalPage()) {
            logger.info("��ʼ����" + sp.getCurrPage() + "ҳ");
            int currPage = sp.getCurrPage();
            HtmlPage page = lianTingWebClient.getCurrentPage(cy.getCookies(),
                String.valueOf(currPage));
            xyzCodexCronService.asynCron(lianTingWebClient.getTr(page), cy.getChsName());
            currPage++;
            sp.setCurrPage(currPage);
            scheduleDao.create(sp);
        }

        return mv;
    }

    private String getTopContext(String city) {
        logger.info("��ʼ��ѯ����[" + city + "]�ĺ����б�");
        WebClient wc = new WebClient();
        wc.setJavaScriptEnabled(false);
        wc.setCssEnabled(false);
        String url = "http://taohaoma.appspot.com";
        //String url = "http://localhost:8080";
        HtmlPage page = null;
        try {
            page = wc.getPage(url + "/app/sale/list?city=" + city);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            logger.info("��ѯ�������" + sw.toString());
        }
        HtmlElement div = page.getElementById("top");
        //String desc = div.asText();
        return div.asXml();

    }

}
