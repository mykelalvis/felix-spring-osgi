package org.ertuo.douche.dao.opration;

import org.ertuo.douche.dao.domain.PostDo;

public interface PostDao {
	
	/**
	 * ͨ��id��ûظ���¼
	 * @param postId
	 * @return
	 */
	PostDo getPostById(String postId);
	
	
	/**
	 * ����һ���ظ���¼
	 * @param postDo
	 */
	void savePost(PostDo postDo);
	

}
