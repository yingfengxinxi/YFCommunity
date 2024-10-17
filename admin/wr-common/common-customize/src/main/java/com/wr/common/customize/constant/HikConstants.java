package com.wr.common.customize.constant;

/**
 * @Author: RainCity
 * @Date: 2021-07-19 15:16:44
 * @Desc: 海康相关常量
 */
public class HikConstants {
    /**
     * 成功
     */
    public static final Integer CODE_SUCCESS = 0;
    /**
     * 失败
     */
    public static final Integer CODE_FAIL = 1;
    /**
     * 合作方key（AK）
     * 65==>21847976
     * 85==>24164922
     */
    //public static final String AK = "27975600";
    public static final String AK = "27931702";
    /**
     * 合作方Secret（SK）
     * 65==>pkNOSZmbIXMGyeVqZnbS
     * 85==>oi0F1Kkm2EiK8WUU1wFQ
     */
    //public static final String SK = "g4ej5JyiJEC80hIRCYEL";
    public static final String SK = "HDVqXOcjJeiAPt6p6Yry";
    /**
     * 接口访问url
     */
    public static final String HIK_HOST = "10443";
    public static final String HIK_IP = "112.122.230.18";
    /**
     * 参数提交方式
     */
    public static final String CONTENT_TYPE = "application/json";
    /**
     * 接口访问前缀
     */
    public static final String PRE_PATH = "/artemis";
    /**
     * 标签（用于区分不同业务组件，建议使用组件标识;支持1-32个数字和小写字母)
     */
    public static final String TAG_ID = "frs";
    /**
     * 方括号（左）
     */
    public static final String SQUARE_BRACKET_LEFT = "[";
    /**
     * 方括号（右）
     */
    public static final String SQUARE_BRACKET_RIGHT = "]";
    /**
     * 默认根节点
     */
    public static final String ROOT_INDEX_CODE = "root000000";
    /**
     * 默认区域编码前5位
     */
    public static final String PRE_REGION_CODE = "01201001";

    /**
     * 门禁事件类型
     * 198657：门被外力开启    199428：胁迫报警   199708：设备防拆报警   199709：设备防拆恢复
     */
    public static final Integer[] EVENT_ACS = {198657, 199428, 199708, 199709};

    /**
     * 烟火事件类型
     * 192513：烟雾检测    192514：烟火检测   192515：火点检测
     */
    public static final Integer[] EVENT_FIRE = {192513, 192514, 192515};

    /**
     * 视频事件类型
     * 131330：视频遮挡  131585：越界侦测    131597：攀高  131605：倒地   930335：高空抛物
     */
    public static final Integer[] EVENT_VIDEO = {131330, 131585, 131597, 131605, 930335};

    /**
     * 可视对讲预警事件类型
     * 983302：设备防拆报警    983303：劫持报警     983556：烟感报警
     * 983560：紧急开关报警    983563：智能锁撬门报警
     */
    public static final Integer[] EVENT_VIS = {983302, 983303, 983556, 983560, 983563};

    //离开区域
    public static final Integer[] LEAVE = {930335};

    /**
     * 预警事件集
     */
    // public static final Integer[] EVENT_WARN = {
    //         // 门禁事件类型 (门被外力开启,胁迫报警,设备防拆报警,设备防拆恢复)
    //         198657, 199428, 199708, 199709,
    //         // 烟火事件类型 (烟雾检测,烟火检测,火点检测)
    //         192513, 192514, 192515,
    //         // 视频事件类型 (视频遮挡,越界侦测,攀高,倒地,高空抛物)
    //         131330, 131585, 131597, 131605, 930335,
    //         // 可视对讲预警事件类型(设备防拆报警,劫持报警,烟感报警,紧急开关报警,智能锁撬门报警)
    //         983302,983303,983556,983560,983563
    // };
    public static final Integer[] EVENT_WARN = {
            // 门禁事件类型 (门被外力开启,胁迫报警,设备防拆报警,设备防拆恢复)
            198657, 199428, 199708, 199709,
            // 视频事件类型 (视频遮挡,越界侦测,攀高,倒地,高空抛物)
            131330, 131585, 131597, 131605, 930335,
            // 可视对讲预警事件类型(设备防拆报警,劫持报警,烟感报警,紧急开关报警)
            983302, 983303, 983556, 983560
    };

    /**
     * 视频事件类型高空抛物【原始事件】
     */
    public static final Integer[] EVENT_WARN_Y = {
            //视频事件类型(高空抛物)
            930335,
    };

    /**
     * 视频事件类型越界侦测【联动事件】
     */
    public static final Integer[] EVENT_WARN_L = {
            //视频事件类型(越界侦测,区域入侵)
            131585,131588
    };

    /**
     * 可视对讲出入事件类型
     * 983297：业主密码开锁 —密码开锁   983299：刷卡开锁    983300：室内机开锁 —户主开锁
     * 983301：中心平台开锁            983301：人脸开锁    983301：指纹开锁
     */
    public static final Integer[] EVENT_VIS_INOUT = {983297, 983299, 983300, 983301, 983309, 983310};

    /**
     * 合法卡比对事件
     */
    public static final Integer[] EVENT_LEGAL_CARD = {198914};

    /**
     * 入场压线事件
     */
    public static final Integer[] EVENT_IN_CRIMPING = {771760130};
    /**
     * 出场压线事件
     */
    public static final Integer[] EVENT_OUT_CRIMPING = {771760133};
    /**
     * 出入场压线事件，取消订阅用
     */
    public static final Integer[] EVENT_CRIMPING = {771760130, 771760133};
}
