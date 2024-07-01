package com.bear.hospital.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("dept")
public class Department {

    private String deptId;
    private String deptName;

}
