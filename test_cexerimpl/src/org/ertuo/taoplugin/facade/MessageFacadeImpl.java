package org.ertuo.taoplugin.facade;


import java.util.Collection;
import java.util.UUID;

import org.ertuo.taoplugin.bean.Message;
import org.ertuo.taoplugin.dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageFacadeImpl implements MessageFacade {
	
	private final MessageDao messageDao;

	@Autowired
	public MessageFacadeImpl(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public void sendMessage(Message message){
		String uuid = UUID.randomUUID().toString();
		message.setUuid(uuid);
		this.messageDao.createMessage(message);
	}
	
	public Collection<Message> listMessages(){
		return this.messageDao.list();
	}
	
}
