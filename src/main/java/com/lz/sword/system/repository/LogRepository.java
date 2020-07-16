package com.lz.sword.system.repository;

import com.lz.sword.basic.BaseRepository;
import com.lz.sword.system.entity.Log;
import org.springframework.stereotype.Repository;

/**
 * 系统日志
 *
 * @author liangzhong
 * @version 1.0 2020-4-13 17:22:36
 */
@Repository
public interface LogRepository extends BaseRepository<Log, Long> {
}
