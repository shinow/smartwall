package link.smartwall.cloud.forum.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_rank")
public class Rank extends ForumBaseEntity {
	@Column(value = "rank_name")
	private String name;

	@Column(value = "rank_min")
	private int rankMin;

	@Column(value = "rank_image")
	private String rankImage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRankMin() {
		return rankMin;
	}

	public void setRankMin(int rankMin) {
		this.rankMin = rankMin;
	}

	public String getRankImage() {
		return rankImage;
	}

	public void setRankImage(String rankImage) {
		this.rankImage = rankImage;
	}
}
