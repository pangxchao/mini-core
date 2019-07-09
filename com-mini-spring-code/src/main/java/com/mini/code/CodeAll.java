package com.mini.code;

public class CodeAll {
    public static void main(String[] args) throws Exception {
        // Bean 代码生成
        CodeBase.main(args);
        CodeBean.main(args);

        // Mapper 代码生成
        CodeMapper.main(args);

        // Dao 代码生成
        CodeDao.main(args);
        CodeDaoImpl.main(args);

        // Service 代码生成
        CodeService.main(args);
        CodeServiceImpl.main(args);
    }
}
