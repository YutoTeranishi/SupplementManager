package com.supMana.supplementmanager;

public class Protein extends Inventory_Type {

    private int remaining_rate;
    private int remaining_times;

    Protein(String name, double init_amount, double total_amount, double one_time_serv, int icon_num, int unit_num) {
        super(name,init_amount,total_amount,one_time_serv,icon_num,unit_num);

        setRemaingRate(cal_RemaingRate(init_amount,total_amount)); //残率
        setRemaining_times(cal_RemaingTimes(total_amount,one_time_serv,getUnit_num()));//残回数
    }

    public void subTotal_amount(double total_amount,double one_time_serv,int times){ //double total_amount(kg),double one_time_serv(g),times倍率
        double tmp_total_amount =0.0;
        int tmp_unit_num = getUnit_num();
        if(tmp_unit_num==0) { //プロテインの場合
            tmp_total_amount = total_amount - convert_g_to_kg(one_time_serv*times);
        }else{ //サプリメントの場合
            tmp_total_amount = total_amount - (one_time_serv*times);
        }
        if(tmp_total_amount<0.0) {
            tmp_total_amount = 0.0;
        }

        setRemaingRate(cal_RemaingRate(getInit_amount(),tmp_total_amount)); //残率の更新
        setRemaining_times(cal_RemaingTimes(tmp_total_amount,one_time_serv,tmp_unit_num)); //残回数の更新

        setTotal_amount(tmp_total_amount);

    }

    public int getrRemaingRate(){
        return remaining_rate;
    }

    public void setRemaingRate(int remaining_rate){
        this.remaining_rate = remaining_rate;
    }

    public int getRemaining_times() {
        return remaining_times;
    }

    public void setRemaining_times(int remaining_times) {
        this.remaining_times = remaining_times;
    }
    private int cal_RemaingTimes(double t_amount, double one_time_serv,int Unit_num) {
        if(Unit_num==0) {
            remaining_times = (int) (convert_kg_to_g(t_amount) / one_time_serv);
        }else{
            remaining_times = (int) (t_amount / one_time_serv);
        }
        return remaining_times;
    }

        //残率計算0,10,25,50,75,100%で分類
    private int cal_RemaingRate(double i_amount, double t_amount){
        int retention = 0;
        retention =(int) ((t_amount / i_amount) *100); //残率計算(小数点以下切捨て)

        //100~0%の25%刻み
        if(retention<=0) {
            return 0;
        }else if(retention <= 10){
            return 10;
        }else if(retention <= 25){
            return 25;
        }else if(retention <= 50){
            return 50;
        }else if(retention <= 75){
            return 75;
        }else {
            return 100;
        }
    }

}
