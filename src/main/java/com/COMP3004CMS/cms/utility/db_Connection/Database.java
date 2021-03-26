package com.COMP3004CMS.cms.utility.db_Connection;

/* ****** INTERFACE  FOR DB COMMUNICATION   ********/

public interface Database {
    /**
     *  Main query method for the db's
     * @param query Dbquery object containing required params for different db types
     * @return DbResult object containing required information in a generic format
     */
    DbResult query(DbQuery query);
    void beginTransaction();
    void rollBackTransaction();
    void commitTransaction();

    /**
     * Checks whether currently querying the db
     * @return true is yes
     */
    boolean isInTransaction();

}
