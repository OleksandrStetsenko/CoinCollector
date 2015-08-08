package ua.edu.sumdu.lab3.group11;

public class Settings {

    //OBJECT_TYPE_ID
    public static final int USER_OBJECT_TYPE_ID = 1;
    public static final int COIN_OBJECT_TYPE_ID = 2;

    //ATTR_ID
    public static final int USER_PASSWORD_ATTR_ID = 1;
    public static final int USER_ADMIN_ATTR_ID = 2;
    public static final int COIN_WEIGHT_ATTR_ID = 3;
    public static final int COIN_YEAR_ATTR_ID = 4;
    public static final int COIN_NAME_ATTR_ID = 5;
    public static final int COIN_METALL_ATTR_ID = 6;
    public static final int COIN_DIAMETER_ATTR_ID = 7;
    public static final int COIN_VALUE_ATTR_ID = 8;
    public static final int USER_COINS_ATTR_ID = 9;

    //QUERY
    public static final String GET_COINS_HEAD = 
            "select obj.name as fullname , obj.object_id as coinID, \n"
            + "diameter_mm.NUMBER_VALUE as diameter_mm, metall.text_value as metall,\n "
            + " name.text_value as name,value.NUMBER_VALUE as value, year.NUMBER_VALUE as year,\n "
            + "weight.NUMBER_VALUE as weight ";
    
    public static final String GET_COINS_FOOT = 
            "join params diameter_mm on diameter_mm.OBJECT_ID = obj.OBJECT_ID and diameter_mm.ATTR_ID= ?\n "
            + "join params metall on metall.OBJECT_ID = obj.OBJECT_ID and metall.ATTR_ID= ? \n"
            + "join params name on name.OBJECT_ID = obj.OBJECT_ID and name.ATTR_ID= ? \n"
            + "join params value on value.OBJECT_ID = obj.OBJECT_ID and value.ATTR_ID= ? \n"
            + "join params year on year.OBJECT_ID = obj.OBJECT_ID and year.ATTR_ID= ? \n"
            + "join params weight on weight.OBJECT_ID = obj.OBJECT_ID and weight.ATTR_ID= ?\n ";
    
    public static final String WHERE_OBJ_TYPE_ID = "where obj.object_type_id = ? ";
    
    public static final String FROM_OBJ = "from objects obj ";
    
    public static final String GET_YEARS = "Select distinct p.number_value year from params p "
            + " where  p.attr_id = ? Order By p.number_value ";
    
    public static final String GET_METALS = "Select distinct p.text_value metall from params p "
            + " where  p.attr_id = ? Order By p.text_value ";
    
    public static final String GET_CONDITIONS_SEARCH_COINS
            = " AND obj.object_id IN (select r.ref_id from refs r where r.object_id = ? )";
    
    public static final String HIERAR_QUERY_HEAD
            = "select tree.name as fullname , tree.coin_id as coinID,  \n"
            + "diameter_mm.NUMBER_VALUE as diameter_mm, metall.text_value as metall,\n "
            + "name.text_value as name,value.NUMBER_VALUE as value, year.NUMBER_VALUE as year,\n"
            + "weight.NUMBER_VALUE as weight \n"
            + "from ( select  t.name name, t.id coin_id, t.type_id type_id \n "
            + "       from (Select o.name name, o.object_id id, o.object_type_id type_id \n"
            + "             from objects o "
            + "              start with o.parent_id = ? "
            + "              connect by prior o.object_id = o.parent_id) t \n"
            + "       where t.type_id = ? ";
        
    public static final String HIERAR_QUERY_FOOT
            = " ) tree "
            + "join params diameter_mm on diameter_mm.OBJECT_ID = tree.coin_id and diameter_mm.ATTR_ID= ? \n"
            + "join params metall on metall.OBJECT_ID = tree.coin_id and metall.ATTR_ID= ? \n"
            + "join params name on name.OBJECT_ID = tree.coin_id and name.ATTR_ID= ? \n"
            + "join params value on value.OBJECT_ID = tree.coin_id and value.ATTR_ID= ? \n "
            + "join params year on year.OBJECT_ID = tree.coin_id and year.ATTR_ID= ? \n"
            + "join params weight on weight.OBJECT_ID = tree.coin_id and weight.ATTR_ID= ? \n";
    
    public static final String GET_COINS_OF_USER
            = " AND t.id IN (select r.ref_id from refs r where r.object_id = ? )";
    
    public static final String GET_HIERAR_ALL_COUNTRIES
            = "Select  level,  o.object_id, o.name "
            + "From objects o "
            + "Start with o.parent_id is null and o.object_type_id = 3 "
            + "Connect by prior o.object_id = o.parent_id "
            + "Order siblings by o.name";
    
    public static final String GET_ALL_COUNTRIES
            = "SELECT o.name, o.object_id, o.parent_id"
            + " FROM objects o"
            + " WHERE o.object_type_id = 4 "
            + " ORDER BY o.name";
    
    public static final String INSERT_OBJ = 
            "INSERT INTO objects VALUES (objects_seq.nextval, ?, ?, ? )";
    
    public static final String INSERT_PARAM_TEXT = 
            "insert into params (OBJECT_ID,ATTR_ID, text_value ) values (?,?,?)";
    
    public static final String INSERT_PARAM_BOOLEAN = 
            "insert into params (OBJECT_ID,ATTR_ID, boolean_value ) values (?,?,?)";
    
    public static final String INSERT_PARAM_NUMBER =
            "insert into params (OBJECT_ID,ATTR_ID, number_value ) values (?,?,?)";
    
    public static final String UPDATE_PARAM_TEXT = 
            "update params set text_value=? where object_id=? and attr_id=? ";
    
    public static final String UPDATE_PARAM_NUMBER =
            "update params set number_value=? where object_id=? and attr_id=? ";
    
    public static final String UPDATE_OBJECT =
            "update objects set name=?, parent_id=? where object_id=?";
    
    public static final String DELETE_COIN_PARAMS = 
            "delete from params where object_id=?";
    
    public static final String DELETE_COIN_OBJECTS = 
            "delete from objects where object_id=?";
    
    public static final String GET_COIN_EXIST =
            "SELECT * FROM refs WHERE object_id = ? AND attr_id = ? AND ref_id = ?";
    
    public static final String DELETE_COIN_FROM_REFS =
            "DELETE FROM refs WHERE object_id = ? AND attr_id = ? AND ref_id = ?";
    
    public static final String JOIN_REFS = "  join refs r\n"
                + " on r.object_id = ? \n"
                + " and r.attr_id = ?\n"
                + " and obj.object_id = r.ref_id\n";
    
    public static final String DELETE_USER = "DELETE FROM objects\n"
                + "WHERE object_id = ?";
    
    public static final String GET_USERS = "SELECT O.OBJECT_ID,  O.PARENT_ID,\n"
                + " O.NAME USERNAME,  PASS.TEXT_VALUE PASSWORD,\n"
                + " ADM.BOOLEAN_VALUE IS_ADMIN \n"
                + "FROM OBJECTS O "
                + "LEFT OUTER JOIN PARAMS PASS ON O.OBJECT_ID = PASS.OBJECT_ID AND  PASS.ATTR_ID = ?\n"
                + "LEFT OUTER JOIN PARAMS ADM ON O.OBJECT_ID = ADM.OBJECT_ID AND  ADM.ATTR_ID = ?\n"
                + "WHERE O.OBJECT_TYPE_ID = ? ";
    
    public static final String USER_BY_NAME = " AND  O.NAME = ?";
    
    public static final String USER_BY_PK =  " AND O.OBJECT_ID = ?";

    public static final String INSERT_REFS = "INSERT INTO refs (object_id, attr_id, ref_id) VALUES (?, ?, ?)";
    
    //LINKS
    public static final String NO_AVALIBLE = "notAvalible.jsp";
    

}
