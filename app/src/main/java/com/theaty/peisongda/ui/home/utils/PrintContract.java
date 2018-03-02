package com.theaty.peisongda.ui.home.utils;

/**
 * Created by lixiangyi on 2018/2/22.
 */

import android.annotation.SuppressLint;

import com.theaty.peisongda.model.peisongda.OrderModel;

import java.nio.charset.Charset;

/**
 * 打印模板
 */
public class PrintContract {
    /**
     * 打印纸一行最大的字节
     */
    private static final int LINE_BYTE_SIZE = 32;
    /**
     * 打印三列时，中间一列的中心线距离打印纸左侧的距离
     */
    private static final int LEFT_LENGTH = 16;
    /**
     * 打印三列时，中间一列的中心线距离打印纸右侧的距离
     */
    private static final int RIGHT_LENGTH = 16;
    /**
     * 打印三列时，第一列汉字最多显示几个文字
     */
    private static final int LEFT_TEXT_MAX_LENGTH = 5;


    /**
     * 获取数据长度 * * @param msg * @return
     */
    @SuppressLint("NewApi")
    private static int getBytesLength(String msg) {
        return msg.getBytes(Charset.forName("GB2312")).length;
    }


    /**
     * 打印两列 * * @param leftText 左侧文字 * @param rightText 右侧文字 * @return
     */
    @SuppressLint("NewApi")
    public static String printTwoData(StringBuilder sb,String leftText, String rightText) {
//        StringBuilder sb = new StringBuilder();
        int leftTextLength = getBytesLength(leftText);
        int rightTextLength = getBytesLength(rightText);
        sb.append(leftText); // 计算两侧文字中间的空格
        //
        int marginBetweenMiddleAndRight = LINE_BYTE_SIZE - leftTextLength - rightTextLength;
        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }
        sb.append(rightText);
        return sb.toString();
    }

    /**
     * 打印三列 * * @param leftText 左侧文字 * @param middleText 中间文字 * @param rightText 右侧文字 * @return
     */
    @SuppressLint("NewApi")
    public static String printThreeData( StringBuilder sb,String leftText, String middleText, String rightText) {
       // 左边最多显示 LEFT_TEXT_MAX_LENGTH 个汉字 + 两个点
        String leftTextOther = "";
        if (leftText.length() > LEFT_TEXT_MAX_LENGTH) {
             leftTextOther =leftText.substring(LEFT_TEXT_MAX_LENGTH, leftText.length());
            leftText = leftText.substring(0, LEFT_TEXT_MAX_LENGTH) ;

        }
        int leftTextLength = getBytesLength(leftText);
        int middleTextLength = getBytesLength(middleText);
        int rightTextLength = getBytesLength(rightText);
        sb.append(leftText); // 计算左侧文字和中间文字的空格长度
//        int marginBetweenLeftAndMiddle = LEFT_LENGTH - leftTextLength - middleTextLength / 2;
        int marginBetweenLeftAndMiddle = LEFT_LENGTH - leftTextLength-4;
        for (int i = 0; i < marginBetweenLeftAndMiddle; i++) {
            sb.append(" ");
        }
        sb.append(middleText); // 计算右侧文字和中间文字的空格长度
//        int marginBetweenMiddleAndRight = RIGHT_LENGTH - middleTextLength / 2 - rightTextLength;
        int marginBetweenMiddleAndRight = RIGHT_LENGTH - middleTextLength-2;
        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }
        // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格
        sb.delete(sb.length() - 1, sb.length()).append(rightText);
        printLeft(sb,leftTextOther);
        return sb.toString();
    }

    public static void printLeft(StringBuilder sb, String leftTextOther){
        if (leftTextOther.length() > LEFT_TEXT_MAX_LENGTH) {
            String other2 = leftTextOther.substring(LEFT_TEXT_MAX_LENGTH, leftTextOther.length());
            leftTextOther =leftTextOther.substring(0, LEFT_TEXT_MAX_LENGTH);
            sb.append("\n");
            sb.append(leftTextOther);
            printLeft(sb,other2);
        }else {
            sb.append("\n");
            sb.append(leftTextOther);
            return;
        }
    }


    /**
     * 打印内容
     */

    public static StringBuilder createXxTxt(String context) {

        StringBuilder builder = new StringBuilder();

        //设置大号字体以及加粗
        builder.append(PrintFormatUtils.getFontSizeCmd(PrintFormatUtils.FONT_BIG));
        builder.append(PrintFormatUtils.getFontBoldCmd(PrintFormatUtils.FONT_BOLD));
        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_CENTER));
        // 标题
        builder.append("送 菜 打 印");
        //换行，调用次数根据换行数来控制
        addLineSeparator(builder);
        addLineSeparator(builder);
        //设置普通字体大小、不加粗
        builder.append(PrintFormatUtils.getFontSizeCmd(PrintFormatUtils.FONT_NORMAL));
        builder.append(PrintFormatUtils.getFontBoldCmd(PrintFormatUtils.FONT_BOLD_CANCEL));

        //内容
        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_CENTER));
        builder.append("送菜详细信息清单");
        addLineSeparator(builder);
        builder.append("------------------------------");
        addLineSeparator(builder);

        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_LEFT));
        printTwoData(builder,"订单编号:","9000000001000011301" );
        addLineSeparator(builder);
        printTwoData(builder,"下单时间:","2018-02-04 16:21:46" );
        addLineSeparator(builder);
        printTwoData(builder,"订单地址:","北京市海淀区西四环和红四季小区 老王餐馆");
        addLineSeparator(builder);
        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_CENTER));
        builder.append("------------------------------");
        addLineSeparator(builder);

        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_LEFT));
        printThreeData(builder,"菜名称","数量","单价" );
        addLineSeparator(builder);
        printThreeData(builder,"大白菜","18斤左右","1.2元" );
        addLineSeparator(builder);
        printThreeData(builder,"胡萝卜","15斤左右","2.2元" );
        addLineSeparator(builder);
        printThreeData(builder,"青椒","10斤左右","14.2元" );
        addLineSeparator(builder);
        printThreeData(builder,"辣椒","3斤左右","6.2元" );
        addLineSeparator(builder);
        printThreeData(builder,"西红柿","6斤左右","5.2元" );
        addLineSeparator(builder);
        printThreeData(builder,"土豆","5斤左右","3.2元\n\n" );

        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_CENTER));
        builder.append("******************************");
        addLineSeparator(builder);
        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_CENTER));
        builder.append("谢谢惠顾，欢迎下次光临！\n\n");
//        ......

        //设置某两列文字间空格数, x需要计算出来
//        addIdenticalStrToStringBuilder(builder, x, " ");

        //切纸
        builder.append(PrintFormatUtils.getCutPaperCmd());

        return builder;
    }
    //type 1未完成  2已完成
    public static StringBuilder createXxTxt(OrderModel orderModel,int type) {

        StringBuilder builder = new StringBuilder();

        //设置大号字体以及加粗
        builder.append(PrintFormatUtils.getFontSizeCmd(PrintFormatUtils.FONT_BIG));
        builder.append(PrintFormatUtils.getFontBoldCmd(PrintFormatUtils.FONT_BOLD));
        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_CENTER));
        // 标题
        builder.append(orderModel.store_name);
        //换行，调用次数根据换行数来控制
        addLineSeparator(builder);
        addLineSeparator(builder);
        builder.append(PrintFormatUtils.getFontSizeCmd(PrintFormatUtils.FONT_MIDDLE));
        builder.append("收货人："+orderModel.reciver_info.reciver_name);
        addLineSeparator(builder);
        //设置普通字体大小、不加粗
        builder.append(PrintFormatUtils.getFontSizeCmd(PrintFormatUtils.FONT_NORMAL));
        builder.append(PrintFormatUtils.getFontBoldCmd(PrintFormatUtils.FONT_BOLD_CANCEL));

        //内容
//        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_CENTER));
//        addLineSeparator(builder);
        builder.append("------------------------------");
        addLineSeparator(builder);

        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_LEFT));
        printTwoData(builder,"订单编号:",orderModel.order_sn );
        addLineSeparator(builder);
        printTwoData(builder,"下单时间:",orderModel.add_time );
        addLineSeparator(builder);
//        printTwoData(builder,"订单地址:",orderModel.reciver_info.address);
//        addLineSeparator(builder);
        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_CENTER));
        builder.append("------------------------------");
        addLineSeparator(builder);

        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_LEFT));
        printThreeData(builder,"菜名称","数量","单价" );
        addLineSeparator(builder);
        if (type==1) {
            if (orderModel.extend_order_goods!=null) {
                for (int i = 0; i <orderModel.extend_order_goods.size() ; i++) {
                    if (orderModel.extend_order_goods.get(i).dishes_unit!=null) {
                        printThreeData(builder,orderModel.extend_order_goods.get(i).goods_name,orderModel.extend_order_goods.get(i).goods_num
                                        +orderModel.extend_order_goods.get(i).dishes_unit+"",
                                orderModel.extend_order_goods.get(i).goods_price+"元" );
                    }else {
                        printThreeData(builder,orderModel.extend_order_goods.get(i).goods_name,orderModel.extend_order_goods.get(i).goods_num
                                        +"斤",
                                orderModel.extend_order_goods.get(i).goods_price+"元" );
                    }

                    addLineSeparator(builder);
                }
            }
        }else {
            if (orderModel.extend_order_goods!=null) {
                for (int i = 0; i <orderModel.extend_order_goods.size() ; i++) {
                    if (orderModel.extend_order_goods.get(i).dishes_unit!=null) {
                        printThreeData(builder,orderModel.extend_order_goods.get(i).goods_name,orderModel.extend_order_goods.get(i).goods_num
                                        +orderModel.extend_order_goods.get(i).dishes_unit,
                                orderModel.extend_order_goods.get(i).goods_price+"元" );
                    }else {
                        printThreeData(builder,orderModel.extend_order_goods.get(i).goods_name,orderModel.extend_order_goods.get(i).goods_num
                                        +"斤",
                                orderModel.extend_order_goods.get(i).goods_price+"元" );
                    }
                    addLineSeparator(builder);
                }
            }
        }
        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_CENTER));
        builder.append("------------------------------");
        addLineSeparator(builder);
        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_RIGHT));
        builder.append("总价："+orderModel.order_amount_all+"元" );
        addLineSeparator(builder);
        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_CENTER));
        builder.append("******************************");
        addLineSeparator(builder);
        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_CENTER));
        builder.append("谢谢惠顾，欢迎下次光临！\n\n\n\n");
//        ......

        //设置某两列文字间空格数, x需要计算出来
//        addIdenticalStrToStringBuilder(builder, x, " ");

        //切纸
        builder.append(PrintFormatUtils.getCutPaperCmd());

        return builder;
    }

    /**
     * 向StringBuilder中添加指定数量的相同字符
     *
     * @param printCount   添加的字符数量
     * @param identicalStr 添加的字符
     */

    private static void addIdenticalStrToStringBuilder(StringBuilder builder, int printCount, String identicalStr) {
        for (int i = 0; i < printCount; i++) {
            builder.append(identicalStr);
        }
    }

    /**
     * 根据字符串截取前指定字节数,按照GBK编码进行截取
     *
     * @param str 原字符串
     * @param len 截取的字节数
     * @return 截取后的字符串
     */
    private static String subStringByGBK(String str, int len) {
        String result = null;
        if (str != null) {
            try {
                byte[] a = str.getBytes("GBK");
                if (a.length <= len) {
                    result = str;
                } else if (len > 0) {
                    result = new String(a, 0, len, "GBK");
                    int length = result.length();
                    if (str.charAt(length - 1) != result.charAt(length - 1)) {
                        if (length < 2) {
                            result = null;
                        } else {
                            result = result.substring(0, length - 1);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 添加换行符
     */
    private static void addLineSeparator(StringBuilder builder) {
        builder.append("\n");
    }

    /**
     * 在GBK编码下，获取其字符串占据的字符个数
     */
    private static int getCharCountByGBKEncoding(String text) {
        try {
            return text.getBytes("GBK").length;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 打印相关配置
     */
    public static class PrintConfig {
        public int maxLength = 30;

        public boolean printBarcode = false;  // 打印条码
        public boolean printQrCode = false;   // 打印二维码
        public boolean printEndText = true;   // 打印结束语
        public boolean needCutPaper = false;  // 是否切纸
    }

}
