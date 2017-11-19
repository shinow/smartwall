package link.smartwall.cloud.forum.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.itekchina.cloud.common.dao.ItekDao;

import link.smartwall.cloud.forum.domain.ForumBaseEntity;

@Service
public class ForumService {
	@Autowired
	private ItekDao dao;

	public String insert(Class<? extends ForumBaseEntity> clazz, String data) {
		ForumBaseEntity fbe = JSON.parseObject(data, clazz);
		fbe.setGuid(this.getGuid());
		fbe.setTenantId(0);
		fbe.setDataTime(new Date());

		dao.insert(fbe);

		return fbe.getGuid();
	}

	public String insert(ForumBaseEntity fbe) {
		fbe.setGuid(this.getGuid());
		fbe.setTenantId(0);
		fbe.setDataTime(new Date());

		dao.insert(fbe);

		return fbe.getGuid();
	}

	private String getGuid() {
		return UUID.randomUUID().toString().toUpperCase().replace("-", "");
	}
}
