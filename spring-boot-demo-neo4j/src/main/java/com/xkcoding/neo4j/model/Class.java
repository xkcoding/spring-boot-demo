package com.xkcoding.neo4j.model;

import com.xkcoding.neo4j.config.CustomIdStrategy;
import com.xkcoding.neo4j.constants.NeoConsts;
import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * <p>
 * 班级节点
 * </p>
 *
 * @package: com.xkcoding.neo4j.model
 * @description: 班级节点
 * @author: yangkai.shen
 * @date: Created in 2018-12-24 14:44
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
@Builder
@NodeEntity
public class Class {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = CustomIdStrategy.class)
    private String id;

    /**
     * 班级名称
     */
    @NonNull
    private String name;

    /**
     * 班级的班主任
     */
    @Relationship(NeoConsts.R_BOSS_OF_CLASS)
    @NonNull
    private Teacher boss;
}
