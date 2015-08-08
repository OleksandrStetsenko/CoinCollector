drop table params;
drop table refs;
drop table attributes;
drop table objects;
drop table object_types;
drop SEQUENCE objects_seq;

-- Create tables
Create table object_types (
    object_type_id Number(20,0) NOT NULL ,
    name Varchar2(100),
    description Varchar2(1000));

    
Create table objects (
    object_id Number(20,0) NOT NULL ,
    parent_id Number(20,0),
    object_type_id Number(20,0) NOT NULL ,
    name Varchar2(100));
    
Create table attributes (
    attr_id Number(20,0) NOT NULL ,
    object_type_id Number(20,0) NOT NULL ,
    name Varchar2(100),
	  type VARCHAR2(25) NOT NULL);
    
Create table params (
    object_id Number(20,0) NOT NULL ,
    attr_id Number(20,0) NOT NULL ,
    text_value Varchar2(1000),
    number_value Number(20,0),
    boolean_value Number(1),
    date_value DATE);

Create table refs (
    object_id Number(20,0) NOT NULL ,
    attr_id Number(20,0) NOT NULL ,
    ref_id Number(20,0) NOT NULL);

-- Create primary keys
Alter table object_types add primary key (object_type_id);
Alter table objects add primary key (object_id);
Alter table attributes add primary key (attr_id);
-- Create indexes
Create Index xif1_params ON params (object_id);
-- Create foreign keys
Alter table objects
add Constraint r_5 foreign key (object_type_id)
references object_types (object_type_id)  on delete cascade;
Alter table attributes
add  foreign key (object_type_id) 
references object_types (object_type_id) on delete cascade;
Alter table objects
add Constraint r_1 foreign key (parent_id)
references objects (object_id)  on delete cascade;
Alter table params
add Constraint r_6 foreign key (object_id)
references objects (object_id) on delete cascade;
Alter table params
add Constraint r_7 foreign key (attr_id)
references attributes (attr_id) ;
Alter table refs
add Constraint r_8 foreign key (object_id)
references objects (object_id) on delete cascade;
Alter table refs
add Constraint r_9 foreign key (attr_id)
references attributes (attr_id);
Alter table refs
add Constraint r_10 foreign key (ref_id)
references objects (object_id) on delete cascade;


--seq
CREATE SEQUENCE objects_seq
START WITH 19
INCREMENT BY 1 
NOMAXVALUE;

commit;