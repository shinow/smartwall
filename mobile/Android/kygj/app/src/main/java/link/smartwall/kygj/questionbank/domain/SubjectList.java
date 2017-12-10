package link.smartwall.kygj.questionbank.domain;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;

import link.smartwall.kygj.questionbank.http.JsonResponseParser;

/**
 * 考试科目列表
 */
@HttpResponse(parser = JsonResponseParser.class)
public class SubjectList extends ArrayList<Subject> {
}
