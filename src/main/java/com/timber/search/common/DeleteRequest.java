package com.timber.search.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 删除请求
 *
 * @author timber
 * 
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}