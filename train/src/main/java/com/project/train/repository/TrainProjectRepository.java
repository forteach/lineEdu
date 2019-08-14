package com.project.train.repository;

import com.project.train.domain.TrainProject;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-14 11:19
 * @version: 1.0
 * @description:
 */
public interface TrainProjectRepository extends JpaRepository<TrainProject, String> {
}
