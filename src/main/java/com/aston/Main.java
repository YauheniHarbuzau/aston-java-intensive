package com.aston;

import com.aston.util.ApacheTomcatEmbedUtil;
import com.aston.util.InitDatabaseUtil;

/**
 * Main-класс - точка входа в приложение
 *
 * @see InitDatabaseUtil
 * @see ApacheTomcatEmbedUtil
 */
public class Main {

    public static void main(String[] args) {
        InitDatabaseUtil.createDatabaseWithTables();
        ApacheTomcatEmbedUtil.start();
    }
}
