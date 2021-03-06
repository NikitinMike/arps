package com.mrs.service.arps;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Getter
class ARPS {

    private String header;
    // строка с кодом 3
    // 1) Номер договора (объекта) (текст)
    private String documentName; //2) Наименование договора (объекта) (текст).
    // 3) Адрес объекта (текст).
    private String documentNumber; //4) Номер документа или наименование передаваемой сметы (текст).
    private String title; //5) Наименование документа (текст).
    // 6) Наименование организации-заказчика (текст).
    private String representativeName; //7) ФИО представителя организации-заказчика (текст).
    // 8) Наименование организации-подрядчика (текст).
    // 9) ФИО представителя организации-подрядчика (текст).
    // 10) Наименование организации-субподрядчика (текст).
    // 11) ФИО представителя организации-субподрядчика (текст).
    private String drafterName; //12) ФИО составителя документа (текст).
    private String controllerName; //13) ФИО контролера документа (текст).
    private String totalCost; //14) Общая стоимость по смете, акту выполненных работ (число).
    private String prices; //15) В каких ценах составлен документ (число).
    private String period; //16) Период, за который составлен документ (число).
    private String code; //17) Код стройки (текст).
    private String name; //18) Наименование стройки (текст).
    private String developmentBasis; //19) Основание для разработки передаваемого документа
    private String frameworkBase; //20) Обозначение нормативной базы, на основании которой составлен документ


    ArrayList<String[]> arps20 = new ArrayList();
    ArrayList<String[]> arps30 = new ArrayList();

    // 50
    // 1) Наименование затрат (текст).
    // 2) Коэффициент (число).
    // 3) Сумма затрат (число).
    // 4) Сумма с учетом данного вида затрат (число).
    //      Итоговая сумма документа после учета в нем данного вида затрат.
    ArrayList<String[]> arps50 = new ArrayList();

    ARPS(String arpsFile){arpsLoad(arpsFile);}

    private boolean arpsLoad(String arpsFile){

        System.out.println(arpsFile);
        try (FileInputStream fis = new FileInputStream(arpsFile)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "Cp866"));
            String line = reader.readLine();
            if (!valid(line)) return false;
            String ls20="";
            while (line != null) {
                String[] ls = line.split("#");
                switch (Integer.parseInt(ls[0])){
                    case 3:
                        documentName=ls[2]; //2) Наименование договора (объекта) (текст).
                        documentNumber=ls[4]; //4) Номер документа или наименование передаваемой сметы (текст).
                        title=ls[5]; //5) Наименование документа (текст).
                        representativeName=ls[7]; //7) ФИО представителя организации-заказчика (текст).
                        drafterName=ls[12]; //12) ФИО составителя документа (текст).
                        controllerName=ls[13]; //13) ФИО контролера документа (текст).
                        totalCost=ls[14]; //14) Общая стоимость по смете, акту выполненных работ (число).
                        prices=ls[15]; //15) В каких ценах составлен документ (число).
                        period=ls[16]; //16) Период, за который составлен документ (число).
                        if (ls.length >= 18) {
                            code = ls[17]; //17) Код стройки (текст).
                            name = ls[18]; //18) Наименование стройки (текст).
                            if (ls.length >= 20) {
                                developmentBasis = ls[19]; //19) Основание для разработки передаваемого документа
                                frameworkBase = ls[20]; //20) Обозначение нормативной базы, на основании которой составлен документ
                            }
                        }
                        break;
                    case 10:
                        arps30.add(new String[]{"",ls[1],"",ls[3],"","","",""}); // заголовок раздела
                        break;
                    case 20:
                        String type = ls[2].matches("С.+|ФСС.+")?"M":"W";
//                        ls[2].matches("ФЕР*");
                        String[] ln20 = new String[]{
                                ls[1], ls[2], ls[3], ls[4], ls[5],
                                ls[6], ls[7], ls[8], ls[9], ls[10],
                                ls[11], ls[12], ls[13], ls[14], ls[15],
                                ls[16], ls[17], ls[18], ls[19], ls[20],
                                ls[21], ls[22], ls[23], ls[24], ls[25],
                                ls[26], ls[27], type};
                        arps20.add(ln20); //
                        ls20 = ln20[0];
                        arps30.add(new String[]{ls20,ln20[1],ln20[2],ln20[3],"","","","","",""});
                        break;
                    case 25: // Тип 25 – поправочные коэффициенты.
                        break;
                    case 30:
                        System.out.println(line);
                        for (int i = 0; i < ls.length; i++)
                            System.out.println(i+":"+ls[i]);
                        if ("2".equals(ls[1]))
                            arps30.add(new String[]{ls20,ls[1],ls[2],ls[3],ls[4],ls[5],"","",""});
                        else
                            if(ls.length>7)
                                arps30.add(new String[]{ls20,ls[1],ls[2],ls[3],ls[4],ls[5],ls[6],ls[7],ls[8]});
                            else arps30.add(new String[]{ls20,ls[1],ls[2],ls[3],ls[4],ls[5],ls[6],"",""});
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
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean valid(String line) { // check for valid format header
        if (line.indexOf("АРПС 1.10")!=0) header=line;
        if (header.indexOf("АРПС 1.10")!=0) return true;
        return false;
    }

    public boolean valid() { return valid("");}

}