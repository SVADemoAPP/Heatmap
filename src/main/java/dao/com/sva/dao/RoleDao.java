/**   
 * @Title: RoleDao.java 
 * @Package com.sva.dao 
 * @Description: 角色DAO
 * @author labelCS   
 * @date 2018年1月11日 下午5:42:40 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;
import com.sva.model.RoleModel;

/** 
 * @ClassName: RoleDao 
 * @Description: 角色DAO
 * @author labelCS 
 * @date 2018年1月11日 下午5:42:40 
 *  
 */
public interface RoleDao
{
    public List<RoleModel> getRoleByAccountId(String id);
}
