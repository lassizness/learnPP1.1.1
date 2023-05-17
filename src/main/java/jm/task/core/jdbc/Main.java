package jm.task.core.jdbc;

import jm.task.core.jdbc.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class Main {
    //ArrayList <String> queryList= new ArrayList<>();
    static HashMap<String, String> queryList = new HashMap<>();

    public static void main(String[] args) throws IOException, SQLException {
        BufferedReader scaner = new BufferedReader(new InputStreamReader(System.in));
        Util.openConnection();
        System.out.println(Util.sendQuery(queryList.get("countUser")));
        Util.closeConnection();

        queryList = initializationListQuery(queryList);
        int numberCommand = 0;
        while (true) {
            switch (numberCommand) {
                case 1:
                    Util.openConnection();
                    try {
                        System.out.println(Util.sendQuery(queryList.get("createTable")));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    Util.closeConnection();
                    numberCommand=0;
                    break;
                case 2:
                    Util.openConnection();
                    try {
                        System.out.println(Util.sendQuery(queryList.get("dropTable")));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    Util.closeConnection();
                    numberCommand=0;
                    break;
                case 3:
                    Util.openConnection();
                    try {
                        System.out.println(Util.sendQuery(queryList.get("clearTable")));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    Util.closeConnection();
                    numberCommand=0;
                    break;
                case 4:
                    Util.openConnection();
                    try {
                        System.out.println(Util.sendQuery(queryList.get("allUser")));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    Util.closeConnection();
                    numberCommand=0;
                    break;
                case 5:
                    Util.openConnection();
                    try {
                        System.out.println(Util.sendQuery(queryList.get("delUser")));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    Util.closeConnection();
                    numberCommand=0;
                    break;
                case 6:
                    System.out.println("Введите количество заносимых в таблицу записей: ");
                    scaner.readLine();
                    Util.openConnection();
                    for (int i = 0; i < Integer.parseInt(scaner.readLine()); i++) {
                        try {
                            System.out.println(Util.sendQuery(queryList.get("addUser") + "('" + genName() + "','" + genLastName() + "'," + getRandomAge() + ")"));
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    Util.closeConnection();
                    numberCommand=0;
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("1. Создать таблицу Users\n" +
                            "2. Удалить таблицу Users\n" +
                            "3. Очистить таблицу Users\n" +
                            "4. Получить список всех записей из таблицы Users\n" +
                            "5. Удалить запись из таблицы Users \n" +
                            "6. Добавить запись в таблицу Users\n" +
                            "7. Завершить программу");
                    System.out.print("Введите номер команды: ");
                    numberCommand = Integer.parseInt(scaner.readLine());
                    break;
            }
        }


        //System.out.println(genName() + " " + genLastName());


    }

    public static HashMap initializationListQuery(HashMap Map) {
        Map.put("createTable", "CREATE TABLE IF NOT EXISTS Users (id SERIAL PRIMARY KEY, name varchar(255), lastname varchar(255), age smallint);");
        Map.put("dropTable", "DROP TABLE IF EXISTS Users;");
        Map.put("clearTable", "TRUNCATE TABLE IF EXISTS Users;");
        Map.put("addUser", "INSERT INTO Users (name, lastname, age) VALUES");
        Map.put("delUser", "DELETE FROM Users WHERE");
        Map.put("allUser", "SELECT * FROM Users;");
        Map.put("countUser", "SELECT count(*) FROM Users;");
        return Map;
    }

    public static String genName() {
        enum Name {
            Елисей, Эрик, Феликс, Григорий, Роман, Давид,
            Анастасия, Виктория, Мария, Елена,
            Наталья

        }
        return Stream.of(Name.values())
                .skip(new Random().nextInt(Name.values().length))
                .findFirst().get().toString();
        //Name randomName= Stream.of(Name.values()).skip(new Random().nextInt(Name.values().length)).findFirst().get();
        //return randomName.toString();
    }

    public static String genLastName() {
        enum LastName {
            Джонсон, Браун, Уокер, Холл,
            Уайт, Томпсон, Мур, Тейлор,
            Андерсон, Дейберт, Миллер

        }
        return Stream.of(LastName.values())
                .skip(new Random().nextInt(LastName.values().length))
                .findFirst().get().toString();
    }

    public static int getRandomAge() {
        return ThreadLocalRandom.current().nextInt(18, 41);
    }
}
