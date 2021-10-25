package cn.topland;

import cn.topland.entity.directus.UserDO;
import cn.topland.util.JsonUtils;
import cn.topland.util.pdf.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class ConsoleTester {

    public static final String DEST = "/home/zhuliangbin/桌面/2.pdf";
    public static final String SRC = "/home/zhuliangbin/桌面/1.pdf";
    public static final String LOGO = "./src/main/resources/img/logo.png";

    public static void main(String[] args) {

//        HtmlToPdfParams params = new HtmlToPdfParamsFactory().quotation();
//        new HtmlToPdfOperation(params).apply("/home/zhuliangbin/桌面/test.html", SRC);
//
//        PdfOperation pdfOperation = QuotationPdfOperation
//                .builder()
//                .title("图澜文化&合作公司 服务报价表")
//                .date(LocalDate.now())
//                .number("123456")
//                .build();
//        pdfOperation.apply(SRC, DEST);
//        String json = "{\"data\":[{\"id\":3,\"name\":\"张三\",\"user_id\":\"asdf2\",\"directus_user\":\"e1f3368a-c080-4069-8333-9e21e1c05868\",\"directus_password\":null,\"employee_id\":\"AS14\",\"avatar\":null,\"email\":null,\"mobile\":\"13318262399\",\"internal_position\":null,\"external_position\":null,\"lead_departments\":null,\"remark\":\"这是一段备注aaaaaaaaaaaaaaaaaaaa\",\"source\":\"OTHER\",\"role\":null,\"auth\":null,\"active\":{\"type\":\"Buffer\",\"data\":[1]},\"creator\":1,\"editor\":null,\"create_time\":\"2021-10-13T14:26:30\",\"last_update_time\":\"2021-10-13T14:26:30\",\"directus_email\":null,\"departments\":[17,18]},{\"id\":4,\"name\":\"张三\",\"user_id\":\"asdf2\",\"directus_user\":\"e1f3368a-c080-4069-8333-9e21e1c05868\",\"directus_password\":null,\"employee_id\":\"AS14\",\"avatar\":null,\"email\":null,\"mobile\":\"13318262399\",\"internal_position\":null,\"external_position\":null,\"lead_departments\":null,\"remark\":\"这是一段备注aaaaaaaaaaaaaaaaaaaa\",\"source\":\"OTHER\",\"role\":null,\"auth\":null,\"active\":{\"type\":\"Buffer\",\"data\":[1]},\"creator\":1,\"editor\":null,\"create_time\":\"2021-10-13T14:26:30\",\"last_update_time\":\"2021-10-13T14:26:30\",\"directus_email\":null,\"departments\":[17,18]}]}";
        String json = "{\"id\":1,\"name\":\"小李\",\"user_id\":\"asdf1\",\"directus_user\":\"e1f3368a-c080-4069-8333-9e21e1c05868\",\"directus_password\":\"123456\",\"employee_id\":\"t123456\",\"avatar\":null,\"email\":\"wework_xiaoli@topland.com\",\"mobile\":null,\"internal_position\":null,\"external_position\":null,\"lead_departments\":null,\"remark\":\"123123aaaa\",\"source\":\"OTHER\",\"role\":19,\"auth\":\"ALL\",\"active\":{\"type\":\"Buffer\",\"data\":[1]},\"creator\":1,\"editor\":1,\"create_time\":\"2021-10-12T15:59:41\",\"last_update_time\":\"2021-10-12T15:59:41\",\"directus_email\":null,\"departments\":[]}";
        UserDO userDo = JsonUtils.parse(json, UserDO.class);
        System.out.println(JsonUtils.toJson(userDo));
//        JsonUtils.read(json).path("data").forEach(u -> {
//
//            System.out.println(u.path("id").asLong());
//        });
    }
}