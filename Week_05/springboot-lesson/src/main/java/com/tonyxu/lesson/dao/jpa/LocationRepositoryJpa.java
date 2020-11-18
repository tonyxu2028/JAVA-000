package com.tonyxu.lesson.dao.jpa;

import com.tonyxu.lesson.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * Created on 2020/11/17.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@Repository
public interface LocationRepositoryJpa extends JpaRepository<Location,Long> {


}
