package org.ertuo.taoplugin.facade;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.ertuo.taoplugin.constant.TaoBaoDev;
import org.springframework.stereotype.Component;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemUpdateRequest;
import com.taobao.api.response.ItemUpdateResponse;

/**
 * top��ͨƽ̨
 * @author mo.duanm
 *
 */
@Component
public class TopFacade {

    private static final Logger logger = Logger.getLogger(TopFacade.class.getName());

    /**
     * @param desc
     * @return
     */
    public Item updateItemDesc(long id, String desc, String title) {
        TaobaoClient client = new DefaultTaobaoClient(TaoBaoDev.App_Api_Host.getValue(),
            TaoBaoDev.App_Key.getValue(), TaoBaoDev.App_Secret.getValue());
        ItemUpdateRequest req = new ItemUpdateRequest();
        //req.setTitle("18602 745 745 ��ͨ3G���� �����人 186��ѡ���� " + org.apache.http.impl.cookie.DateUtils.formatDate(new Date(), "MMdd") + "����");
        req.setDesc(desc);
        req.setTitle(title);
        req.setNumIid(id);
        try {
            ItemUpdateResponse response = client.execute(req);
            logger.info("���͵��Ա����" + ToStringBuilder.reflectionToString(response,ToStringStyle.MULTI_LINE_STYLE)
                );
            return response.getItem();
        } catch (ApiException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            logger.info("����topʧ�ܣ�" + sw.toString());
        }
        return null;
    }

}
