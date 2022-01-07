package com.supMana.supplementmanager;

public abstract class Inventory_Type {
    private  String name;
    private double init_amount; //初期容量(kg)
    private double total_amount; //現在の内容量(kg)
    private double one_time_serv; //一回の量(g)
    private int icon_num; //アイコンの種類
    private int unit_num; //単位

    Inventory_Type(String name, double init_amount, double total_amount, double one_time_serv, int icon_num, int unit_num){
        this.name =name;
        this.init_amount=init_amount;
        this.total_amount=total_amount;
        this.one_time_serv=one_time_serv;
        this.icon_num=icon_num;
        this.unit_num=unit_num;
    }

    public String getUnit() {
        String unit;
        switch (unit_num){
            case 0: unit = "食";break;
            case 1: unit = "回";break;
            default:unit = "食_";break;
        }
        return unit;
    }

    public void setUnit_num(int unit_num) {
        this.unit_num = unit_num;
    }

    public int getUnit_num() {
        return unit_num;
    }

    public double getInit_amount() {
        return init_amount;
    }

    public void setInit_amount(double init_amount) {
        this.init_amount = init_amount;
    }

    public double getOne_time_serv() {
        return one_time_serv;
    }

    public void setOne_time_serv(double one_time_serv) {
        this.one_time_serv = one_time_serv;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
    public void subTotal_amount(double total_amount,double one_time_serv){ //double total_amount(kg),double one_time_serv(g)
        setTotal_amount(total_amount - convert_g_to_kg(one_time_serv));
    }

    public int getIcon_num() {
        return icon_num;
    }

    public void setIcon_num(int icon_num) {
        this.icon_num = icon_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //残量計算
    private double cal_Remaining_amount(double t_amount,double o_t_serv,int
            magnification){

        double new_total_amount =0.0;
        new_total_amount = t_amount - (o_t_serv*magnification); //残量

        return new_total_amount;
    }

    protected double convert_kg_to_g(double kg){
        double g=0;
        g = kg*1000;
        return g;
    }

    protected double convert_g_to_kg(double g){
        double kg=0;
        kg = g/1000;
        return kg;
    }
}
