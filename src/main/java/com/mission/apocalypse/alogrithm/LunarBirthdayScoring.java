package com.mission.apocalypse.alogrithm;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LunarBirthdayScoring {

    // 定义五行相生相克关系的分数
    private static final int BASE_SCORE = 50;
    private static final int SCORE_PER_RELATIONSHIP = 10;
    private static final int SCORE_SAME_ELEMENT = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 输入日期、时间、性别和历法类型
        System.out.println("请输入生日（格式：1995-04-16 22:00 男 阴历）：");
        String input = scanner.nextLine();

        // 解析输入
        String[] parts = input.split(" ");
        String date = parts[0];
        String time = parts[1];
        String gender = parts[2];
        String calendarType = parts[3];

        LocalDate solarDate;

        // 如果是阳历，不需要转换
        if (calendarType.equals("阳历")) {
            solarDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            // 如果是阴历，需要转换为阳历
            solarDate = convertLunarToSolar(date);
        }

        String[] ymd = date.split("-");
        int year = Integer.parseInt(ymd[0]);
        int month = Integer.parseInt(ymd[1]);
        int day = Integer.parseInt(ymd[2]);
        int hour = Integer.parseInt(time.split(":")[0]);

        // 转换为天干地支
        String yearTG = getYearTG(year);
        String monthTG = getMonthTG(year, month);
        String dayTG = getDayTG(year, month, day);
        String hourTG = getHourTG(dayTG, hour);

        // 计算分数
        int score = calculateScore(yearTG, monthTG, dayTG, hourTG);

        // 确保分数不超过100
        if (score > 100) {
            score = 100;
        }

        // 计算星座
        String zodiacSign = calculateZodiacSign(solarDate);

        // 输出结果
        System.out.println("根据五行相生相克原则，该农历生日的总得分为：" + score + " 分");
        System.out.println("星座: " + zodiacSign);

        // 打印详细的分数和十神分析
        printDetailedScore(yearTG, monthTG, dayTG, hourTG, score, gender);

        scanner.close();
    }

    private static LocalDate convertLunarToSolar(String lunarDate) {
        // 需要使用第三方库进行阴历到阳历的转换
        // 这里只是示例，实际使用中需要替换为正确的转换代码
        // 例如，可以使用开源库，如ChineseLunarCalendar等来进行转换
        return LocalDate.of(1995, 6, 2);  // 示例中的固定值，实际应转换
    }

    private static int calculateScore(String yearTG, String monthTG, String dayTG, String hourTG) {
        int score = BASE_SCORE;
        System.out.println("基础分: " + BASE_SCORE);

        // 获取每一部分的天干和地支
        char yearT = yearTG.charAt(0);
        char yearZ = yearTG.charAt(1);
        char monthT = monthTG.charAt(0);
        char monthZ = monthTG.charAt(1);
        char dayT = dayTG.charAt(0);
        char dayZ = dayTG.charAt(1);
        char hourT = hourTG.charAt(0);
        char hourZ = hourTG.charAt(1);

        // 天干五行对应关系
        String[] tianGan = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
        String[] tianGanWuXing = {"木", "木", "火", "火", "土", "土", "金", "金", "水", "水"};

        // 地支五行对应关系
        String[] diZhi = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
        String[] diZhiWuXing = {"水", "土", "木", "木", "土", "火", "火", "土", "金", "金", "土", "水"};

        // 获取每一部分的五行属性
        String yearT_WX = tianGanWuXing[getIndex(tianGan, yearT)];
        String yearZ_WX = diZhiWuXing[getIndex(diZhi, yearZ)];
        String monthT_WX = tianGanWuXing[getIndex(tianGan, monthT)];
        String monthZ_WX = diZhiWuXing[getIndex(diZhi, monthZ)];
        String dayT_WX = tianGanWuXing[getIndex(tianGan, dayT)];
        String dayZ_WX = diZhiWuXing[getIndex(diZhi, dayZ)];
        String hourT_WX = tianGanWuXing[getIndex(tianGan, hourT)];
        String hourZ_WX = diZhiWuXing[getIndex(diZhi, hourZ)];

        // 计算相生相克得分
        int yearScore = getScore(yearT_WX, yearZ_WX);
        int monthScore = getScore(monthT_WX, monthZ_WX);
        int dayScore = getScore(dayT_WX, dayZ_WX);
        int hourScore = getScore(hourT_WX, hourZ_WX);

        score += yearScore;
        score += monthScore;
        score += dayScore;
        score += hourScore;

        System.out.println("年柱得分: " + yearScore);
        System.out.println("月柱得分: " + monthScore);
        System.out.println("日柱得分: " + dayScore);
        System.out.println("时柱得分: " + hourScore);

        return score;
    }

    private static int getIndex(String[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].charAt(0) == c) {
                return i;
            }
        }
        return -1;
    }

    private static int getScore(String t, String z) {
        if (t.equals("木") && z.equals("火") ||
                t.equals("火") && z.equals("土") ||
                t.equals("土") && z.equals("金") ||
                t.equals("金") && z.equals("水") ||
                t.equals("水") && z.equals("木")) {
            return SCORE_PER_RELATIONSHIP;
        }

        if (t.equals("木") && z.equals("土") ||
                t.equals("火") && z.equals("金") ||
                t.equals("土") && z.equals("水") ||
                t.equals("金") && z.equals("木") ||
                t.equals("水") && z.equals("火")) {
            return -SCORE_PER_RELATIONSHIP;
        }

        if (t.equals(z)) {
            return SCORE_SAME_ELEMENT;
        }

        return 0;
    }

    // 简化版的年干支计算（1995年为乙亥年）
    private static String getYearTG(int year) {
        String[] gan = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
        String[] zhi = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
        int baseYear = 1984;  // 甲子年
        int offset = year - baseYear;
        return gan[offset % 10] + zhi[offset % 12];
    }

    // 简化版的月干支计算
    private static String getMonthTG(int year, int month) {
        String[] gan = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
        String[] zhi = {"寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥", "子", "丑"};
        int yearGanIndex = (year - 4) % 10;  // 从甲开始的年天干索引
        int offset = (yearGanIndex % 5) * 2 + month - 1;  // 月干索引的计算
        return gan[offset % 10] + zhi[month - 1];
    }

    // 简化版的日干支计算（假设某日为甲子日）
    private static String getDayTG(int year, int month, int day) {
        // 使用已知的干支日进行计算或查表
        return "乙巳";  // 示例中的固定值
    }

    // 简化版的时干支计算
    private static String getHourTG(String dayTG, int hour) {
        String[] gan = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
        String[] zhi = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
        int dayGanIndex = getIndex(gan, dayTG.charAt(0));  // 获取日干索引
        int offset = (dayGanIndex % 5) * 2 + (hour / 2);  // 时干索引的计算
        return gan[offset % 10] + zhi[hour / 2];
    }

    private static void printDetailedScore(String yearTG, String monthTG, String dayTG, String hourTG, int totalScore, String gender) {
        System.out.println("八字命盘：");
        System.out.println("年柱: " + yearTG + " | 十神: " + getShiShen("乙", yearTG.charAt(0)));
        System.out.println("月柱: " + monthTG + " | 十神: " + getShiShen("乙", monthTG.charAt(0)));
        System.out.println("日柱: " + dayTG + " | 十神: 日主");
        System.out.println("时柱: " + hourTG + " | 十神: " + getShiShen("乙", hourTG.charAt(0)));
        System.out.println("总得分: " + totalScore);
        System.out.println("性别: " + gender);
        System.out.println("性格分析: " + analyzePersonality(yearTG, monthTG, dayTG, hourTG, gender));
        System.out.println("家庭分析: " + analyzeFamily(yearTG, monthTG, dayTG, hourTG, gender));
        System.out.println("事业分析: " + analyzeCareer(yearTG, monthTG, dayTG, hourTG, gender));
        System.out.println("感情分析: " + analyzeLove(yearTG, monthTG, dayTG, hourTG, gender));
    }

    private static String getShiShen(String dayTianGan, char tianGan) {
        String shiShen = "";
        switch (dayTianGan) {
            case "甲":
            case "乙":
                shiShen = getShiShenForJiaYi(tianGan);
                break;
            // 其他日干的情况可以继续扩展
        }
        return shiShen;
    }

    private static String getShiShenForJiaYi(char tianGan) {
        switch (tianGan) {
            case '甲':
            case '乙':
                return "比肩";
            case '丙':
            case '丁':
                return "伤官";
            case '戊':
            case '己':
                return "偏财";
            case '庚':
            case '辛':
                return "七杀";
            case '壬':
            case '癸':
                return "正印";
            default:
                return "";
        }
    }

    private static String calculateZodiacSign(LocalDate date) {
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        switch (month) {
            case 1:
                return (day < 20) ? "摩羯座" : "水瓶座";
            case 2:
                return (day < 19) ? "水瓶座" : "双鱼座";
            case 3:
                return (day < 21) ? "双鱼座" : "白羊座";
            case 4:
                return (day < 20) ? "白羊座" : "金牛座";
            case 5:
                return (day < 21) ? "金牛座" : "双子座";
            case 6:
                return (day < 21) ? "双子座" : "巨蟹座";
            case 7:
                return (day < 23) ? "巨蟹座" : "狮子座";
            case 8:
                return (day < 23) ? "狮子座" : "处女座";
            case 9:
                return (day < 23) ? "处女座" : "天秤座";
            case 10:
                return (day < 23) ? "天秤座" : "天蝎座";
            case 11:
                return (day < 22) ? "天蝎座" : "射手座";
            case 12:
                return (day < 22) ? "射手座" : "摩羯座";
            default:
                return "";
        }
    }

    private static String analyzePersonality(String yearTG, String monthTG, String dayTG, String hourTG, String gender) {
        // 简化版的性格分析，根据八字详细分析
        StringBuilder personality = new StringBuilder();
        personality.append("性格特征：");

        if (yearTG.contains("乙")) personality.append(" 温和、仁慈、细腻。");
        if (monthTG.contains("丁")) personality.append(" 创意丰富，有表现欲。");
        if (dayTG.contains("乙")) personality.append(" 喜好和平，适应能力强。");
        if (hourTG.contains("辛")) personality.append(" 坚毅，面对挑战。");

        return personality.toString();
    }

    private static String analyzeFamily(String yearTG, String monthTG, String dayTG, String hourTG, String gender) {
        // 简化版的家庭分析，根据八字详细分析
        StringBuilder family = new StringBuilder();
        family.append("家庭关系：");

        if (yearTG.contains("乙")) family.append(" 兄弟姐妹关系和谐。");
        if (monthTG.contains("丁")) family.append(" 父母对子女期待高。");
        if (dayTG.contains("乙")) family.append(" 家庭氛围温馨。");
        if (hourTG.contains("辛")) family.append(" 晚年有子女关爱。");

        return family.toString();
    }

    private static String analyzeCareer(String yearTG, String monthTG, String dayTG, String hourTG, String gender) {
        // 简化版的事业分析，根据八字详细分析
        StringBuilder career = new StringBuilder();
        career.append("事业发展：");

        if (yearTG.contains("乙")) career.append(" 适合从事教育、文化领域。");
        if (monthTG.contains("丁")) career.append(" 艺术、创意领域有成就。");
        if (dayTG.contains("乙")) career.append(" 适应力强，适合多种职业。");
        if (hourTG.contains("辛")) career.append(" 适合管理和高压工作。");

        return career.toString();
    }

    private static String analyzeLove(String yearTG, String monthTG, String dayTG, String hourTG, String gender) {
        // 简化版的感情分析，根据八字详细分析
        StringBuilder love = new StringBuilder();
        love.append("感情生活：");

        if (yearTG.contains("乙")) love.append(" 感情细腻，重视和谐。");
        if (monthTG.contains("丁")) love.append(" 对感情有浪漫追求。");
        if (dayTG.contains("乙")) love.append(" 喜欢稳定的关系。");
        if (hourTG.contains("辛")) love.append(" 伴侣关系中会面临挑战，但能克服。");

        return love.toString();
    }

    //
}
