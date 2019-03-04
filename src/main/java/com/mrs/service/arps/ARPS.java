package com.mrs.service.arps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

public class ARPS {

    //-1) Номер договора (объекта) (текст)
    String title; //2) Наименование договора (объекта) (текст).
    //-3) Адрес объекта (текст).
    String documentNumber; //4) Номер документа или наименование передаваемой сметы (текст).
    String documentName; //5) Наименование документа (текст).
    //-6) Наименование организации-заказчика (текст).
    String representativeName; //7) ФИО представителя организации-заказчика (текст).
    //-8) Наименование организации-подрядчика (текст).
    //-9) ФИО представителя организации-подрядчика (текст).
    //-10) Наименование организации-субподрядчика (текст).
    //-11) ФИО представителя организации-субподрядчика (текст).
    String drafterName; //12) ФИО составителя документа (текст).
    String controllerName; //13) ФИО контролера документа (текст).
    String totalCost; //14) Общая стоимость по смете, акту выполненных работ (число).
    String prices; //15) В каких ценах составлен документ (число).
    String period; //16) Период, за который составлен документ (число).
    String code; //17) Код стройки (текст).
    String name; //18) Наименование стройки (текст).
    String developmentBasis; //19) Основание для разработки передаваемого документа
    String frameworkBase; //20) Обозначение нормативной базы, на основании которой составлен документ

//    ArrayList<String> arps3 = new ArrayList();
    ArrayList<String[]> arps20 = new ArrayList();
    ArrayList<String[]> arps30 = new ArrayList();
    ArrayList<String[]> arps50 = new ArrayList();
//        Map<String,String[]> arps30 = null;

    boolean arpsLoad(String arpsFile){

        BufferedReader reader;
        try {
            FileInputStream fis =  new FileInputStream(arpsFile);
            reader = new BufferedReader(new InputStreamReader(fis,"Cp866"));
            String line = reader.readLine();
            String ls20="";
            while (line != null) {
                if (line!=null) {
//                System.out.println(line);
                    String ls[]=line.split("#");
                    switch (Integer.parseInt(ls[0])){
                        case 3:
//                            arps3.add(line);
//                            for (int i = 1; i < ls.length; i++) {
//                                System.out.println();
//                                System.out.printf("%d:%s",i,ls[i]);
//                                if (!ls[i].isEmpty()) arps3.add(ls[i]);
//                            }
//                                System.out.print(ls[i]+" ");
                             title=ls[2]; //2) Наименование договора (объекта) (текст).
                            //-3) Адрес объекта (текст).
                             documentNumber=ls[4]; //4) Номер документа или наименование передаваемой сметы (текст).
                             documentName=ls[5]; //5) Наименование документа (текст).
                            //-6) Наименование организации-заказчика (текст).
                             representativeName=ls[7]; //7) ФИО представителя организации-заказчика (текст).
                            //-8) Наименование организации-подрядчика (текст).
                            //-9) ФИО представителя организации-подрядчика (текст).
                            //-10) Наименование организации-субподрядчика (текст).
                            //-11) ФИО представителя организации-субподрядчика (текст).
                             drafterName=ls[12]; //12) ФИО составителя документа (текст).
                             controllerName=ls[13]; //13) ФИО контролера документа (текст).
                             totalCost=ls[14]; //14) Общая стоимость по смете, акту выполненных работ (число).
                             prices=ls[15]; //15) В каких ценах составлен документ (число).
                             period=ls[16]; //16) Период, за который составлен документ (число).
                             code=ls[17]; //17) Код стройки (текст).
                             name=ls[18]; //18) Наименование стройки (текст).
                             developmentBasis=ls[19]; //19) Основание для разработки передаваемого документа
                             frameworkBase=ls[20]; //20) Обозначение нормативной базы, на основании которой составлен документ
                            break;
                        case 10:
//                            arps30.add(new String[]{ls[1],"","",ls[3],"","","",""}); // заголовок раздела
//                            System.out.println(ls[1]);
                            arps30.add(new String[]{"",ls[1],"",ls[3],"","","",""}); // заголовок раздела
                            break;
                        case 20:
//                            arps20.add(line);
//                            String ln20 = "";
//                            for (int i = 1; i < ls.length; i++)
//                                if (!ls[i].isEmpty())
//                                ln20 += ls[i]+", ";
                            String ln20[]=new String[]{
                                    ls[1],ls[2],ls[3],ls[4],ls[5],
                                    ls[6],ls[7],ls[8],ls[9],ls[10],
                                    ls[11],ls[12],ls[13],ls[14],ls[15],
                                    ls[16],ls[17],ls[18],ls[19],ls[20],
                                    ls[21],ls[22],ls[23],ls[24],ls[25],
                                    ls[26],ls[27]};
                            arps20.add(ln20); //
                            ls20 = ln20[0];
//                            arps30.add(new String[]{ls20,"","","","","","","","",""});
                            arps30.add(new String[]{ls20,ln20[1],ln20[2],ln20[3],"","","","","",""});
                            break;
                        case 25: // Тип 25 – поправочные коэффициенты.
//                        1) Тип поправки (число):
//                            0 – коэффициент учета инфляции (коэффициент пересчета сметных цен),
//                            1 – коэффициент зимнего удорожания,
//                            2 – норматив накладных расходов,
//                            3 – норматив сметной прибыли,
//                            4 – коэффициент учета условий работ,
//                            10 – прочие коэффициенты.
//                        2) К чему относится поправка (число):
//                            0 – заработная плата,
//                            1 – эксплуатация машин и механизмов,
//                            2 – стоимость материалов,
//                            3 – заработная плата механизаторов,
//                            4 – трудозатраты основных рабочих,
//                            5 – трудозатраты механизаторов,
//                            10 – прямые затраты.
//                        3) Действие (число):
//                            0 – умножение,
//                            1 – деление,
//                            2 – сложение,
//                            3 – вычитание.
//                        4) Значение поправочного коэффициента (число).
//                            int ln20i=arps20.size();
//                            k[ln20i][ls[1]]=0.0;
                            break;
                        case 30:
//                            arps30.add(line);
//                            String ln30 = "";
//                            for (int i = 1; i < ls.length; i++)
//                                if (!ls[i].isEmpty())
//                                    ln30 += ls[i]+", ";
//                            System.out.println(ls.length+" : "+line);
                            if ("2".compareTo(ls[1])==0)
                                arps30.add(new String[]{ls20,ls[1],ls[2],ls[3],ls[4],ls[5],"","",""});
                            else arps30.add(new String[]{ls20,ls[1],ls[2],ls[3],ls[4],ls[5],ls[6],ls[7],ls[8]});
//                            arps30.put(ls20,new String[]{ls[1],ls[2],ls[3],ls[4],ls[5],"","",""});
                            break;
                        case 0:
                            break;
                        case 50:
                            arps50.add(new String[]{ls[1],ls[4]});
                            break;
                        default:
                            System.out.printf(" %d[%d]: ",Integer.parseInt(ls[0]),ls.length);
                            for (int i = 1; i < ls.length; i++)
                                System.out.print(ls[i]+" ");
                            System.out.println();
                            break;
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}

