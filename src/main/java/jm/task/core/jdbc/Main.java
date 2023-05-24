package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class Main {
    static HashMap<String, String> queryList = new HashMap<>();
    private static boolean switcher = false;//true для теста hibernate, false jbdc.
    private final static UserService userService = new UserServiceImpl();
    static Main main = new Main();


    public static void main(String[] args) throws IOException, SQLException, IllegalAccessException {
        BufferedReader scaner = new BufferedReader(new InputStreamReader(System.in));
        queryList = initializationListQuery(queryList);
        int numberCommand = 0;

        System.out.println("Выберите метод работы с бд:\n" +
                "1.JDBC\n" +
                "2.HIBERNATE\n");
        System.out.print("Укажите вариант подключения:");

        if (Integer.parseInt(scaner.readLine()) == 1) {
            main.setSwitcher(false);
        } else {
            main.setSwitcher(true);
        }

        while (true) {
            switch (numberCommand) {
                case 1:
                    userService.createUsersTable();
                    System.out.println("Таблица Users создана.");
                    numberCommand = 0;
                    break;
                case 2:
                    userService.dropUsersTable();
                    System.out.println("Таблица Users удалена.");
                    numberCommand = 0;
                    break;
                case 3:
                    userService.cleanUsersTable();
                    System.out.println("Таблица Users очищена.");
                    numberCommand = 0;
                    break;
                case 4:
                    List<User> userList = userService.getAllUsers();
                    System.out.println("name" + "\t\t" + "lastname" + "\t\t" + "age");
                    for (User user : userList) {
                        for (Field field : user.getClass().getDeclaredFields()) {
                            field.setAccessible(true);
                            Object value = field.get(user);
                            if (value != null) {
                                System.out.print(value + "\t\t");
                            }
                        }
                        System.out.println("");
                    }
                    numberCommand = 0;
                    break;
                case 5:

                    System.out.println("1. Удаление по id");
                    switch (Integer.parseInt(scaner.readLine())) {
                        case 1:
                            System.out.print("Введите id для удаления:");
                            userService.removeUserById(Integer.parseInt(scaner.readLine()));
                            break;
                    }
                    System.out.println("Запись удалена.");
                    numberCommand = 0;
                    break;
                case 6:
                    System.out.println("Введите количество заносимых в таблицу записей: ");
                    int count = 0;
                    count = Integer.parseInt(scaner.readLine());
                    for (int i = 0; i < count; i++) {
                        userService.saveUser(genName(), genLastName(), (byte) getRandomAge());
                    }
                    numberCommand = 0;
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
    }

    public static HashMap initializationListQuery(HashMap Map) {
        Map.put("createTable", "CREATE TABLE IF NOT EXISTS Users (id SERIAL PRIMARY KEY, name varchar(255), lastname varchar(255), age smallint);");
        Map.put("dropTable", "DROP TABLE IF EXISTS Users;");
        Map.put("clearTable", "TRUNCATE TABLE  IF EXISTS Users;");
        Map.put("addUser", "INSERT INTO Users (name, lastname, age) VALUES ");
        Map.put("delUser", "DELETE FROM Users WHERE ");
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

    public static String getQueryList(String key) {
        return queryList.get(key);
    }

    public static String[][] splitString(String str, String split1, String split2) {

        if (str != "") {
            String[] rows = str.split(split1);
            String[][] arr = new String[rows.length][];
            for (int i = 0; i < rows.length; i++) {
                String[] cols = rows[i].split(split2);
                arr[i] = new String[cols.length];
                for (int j = 0; j < cols.length; j++) {
                    arr[i][j] = cols[j];
                }
            }
            return arr;
        } else {
            String[][] arr = new String[0][];
            return arr;
        }

    }

    public void setSwitcher(boolean switcher) {
        this.switcher = switcher;
    }

    public boolean getSwitcher() {
        return switcher;
    }
}
