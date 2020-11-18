package com.tonyxu.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created on 2020/11/16.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Lesson {

    private String lessonName;

    private int lessonCourseCount;
}
