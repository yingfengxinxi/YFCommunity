<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
            <el-form-item label="小区名称">
                <el-select v-model="queryParams.communityId" placeholder="请选择小区" size="small">
                    <el-option
                            v-for="item in communityList"
                            :key="item.communityId"
                            :label="item.communityName"
                            :value="item.communityId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="事件类型" prop="eventType">
                <el-select  v-model="queryParams.eventType" placeholder="请选择事件类型" clearable size="small">
                    <el-option
                            v-for="dict in dict.type.sys_event_type"
                            :key="dict.value"
                            :label="dict.label"
                            :value="dict.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="记录时间">
                <el-date-picker v-model="dateRange" size="small" style="width: 240px"
                                value-format="yyyy-MM-dd" type="daterange" range-separator="-"
                                start-placeholder="开始日期" end-placeholder="结束日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
            <el-row :gutter="10" class="mb8">
                <el-col :span="1.5">
                    <el-button
                            type="danger"
                            plain
                            icon="el-icon-delete"
                            size="mini"
                            :disabled="multiple"
                            @click="handleDelete"
                            v-hasPermi="['govern:warn:remove']"
                    >删除</el-button>
                </el-col>
            </el-row>
        </el-form>
        <el-table v-loading="loading" :data="eventList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="事件规则名称" align="center" prop="eventName" :show-overflow-tooltip="true"/>
            <el-table-column label="事件状态" align="center" prop="status">
                <template slot-scope="scope">
                    {{ statusFormatter(scope.row.status) }}
                </template>
            </el-table-column>
            <el-table-column label="事件类别" align="center" prop="ability" :show-overflow-tooltip="true"/>
            <el-table-column label="事件发生时间" prop="happenTime" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                    <span>{{ momentFormat(scope.row.happenTime) }}</span>
                </template>
            </el-table-column>
            <el-table-column label="事件结束时间" prop="stopTime" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                    <span>{{ momentFormat(scope.row.stopTime) }}</span>
                </template>
            </el-table-column>
            <el-table-column label="事件记录时间 " align="center" prop="recordTime" :show-overflow-tooltip="true"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-view"
                            v-hasPermi="['govern:warn:query']"
                            @click="handleView(scope.row)"
                    >查看</el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getWarnList"/>

        <!--  查看  -->
        <el-dialog :title="title" :visible.sync="showDetail" width="60%">
            <AcsData :warnEvent="form" v-if="acsShow"/>
            <VisData :warnEvent="form" v-if="visShow"/>
            <VideoData :warnEvent="form" v-if="videoShow"/>
            <FireData :warnEvent="form" v-if="fireShow"/>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancel">关 闭</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import AcsData from "./acsData";
import VisData from "./visData";
import VideoData from "./videoData";
import FireData from "./fireData";
import { getCurrentList } from "@/api/govern/community"
import {selectEventList, selectEventById, removeEventByIds} from "@/api/govern/security/warn";
import {equals, isEmpty} from "@/utils/common";

export default {
    name: "WarnEvent",
    dicts: ['sys_event_type'],
    components: {AcsData,VisData,VideoData,FireData},
    data() {
        return {
            //小区
            communityList:[],
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                communityId:undefined,
                eventType:930335,
                startTime:'',
                endTime:'',
            },
            // 日期范围
            dateRange: [],
            // 遮罩层
            loading: false,
            //预警
            eventList:[],
            // 总条数
            total: 0,
            // 选中数组
            ids: [],
            // 非多个禁用
            multiple: true,
            title:'',
            showDetail:false,
            acsShow:false,
            visShow:false,
            videoShow:false,
            fireShow: false,
            //门禁事件类型
            acsEvent:[198657,199428,199708,199709],
            //可视对讲事件类型
            visEvent:[983302,983303,983556,983560,983563],
            //烟火事件类型
            fireEvent:[192513,192514,192515],
            //视频事件类型
            videoEvent:[131330,131585,131597,131605,930335],
            form:{},
        };
    },
    created() {
        this.listCommunity();
    },
    methods: {
        // 获取小区数据
        listCommunity() {
            getCurrentList().then(response => {
                this.communityList = response.data;
                if (this.communityList.length > 0) {
                    this.queryParams.communityId = this.communityList[0].communityId
                    this.getWarnList();
                }
            });
        },
        /** 查询列表 */
        getWarnList(){
            if(this.dateRange  == null){
                this.dateRange=[];
            } else {
                this.queryParams.startTime = this.dateRange[0];
                this.queryParams.endTime = this.dateRange[1];
            }
            selectEventList(this.queryParams).then(response=>{
                this.eventList= response.rows;
                this.total = response.total;
                this.loading = false;
            })
        },
        /** 搜索按钮操作 */
        handleQuery() {
            this.queryParams.pageNum = 1;
            this.queryParams.pageSize = 10;
            this.getWarnList();
        },
        /** 重置按钮操作 */
        resetQuery() {
            this.queryParams.pageNum = 1;
            this.queryParams.pageSize = 10;
            this.queryParams.eventType = 930335;
            this.dateRange = []
            this.resetForm("queryForm");
            this.handleQuery();
        },

        // 多选框选中数据
        handleSelectionChange(selection) {
            this.ids = selection.map(item => item.warnId)
            this.multiple = !selection.length
        },
        handleDelete(){
            const warnIds= this.ids;
            this.$confirm('是否确认删除所选中的预警数据?', "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(function() {
                return removeEventByIds(warnIds);
            }).then(() => {
                this.getWarnList();
                this.msgSuccess("删除成功");
            })
        },
        eventFormatter(eventType){
            return this.selectDictLabel(this.dict.type.sys_event_type, eventType);
        },
        statusFormatter(status){
            if(equals(status,'0')){
                return "瞬时"
            }else if(equals(status,'1')){
                return "开始"
            }else if(equals(status,'2')){
                return "停止"
            }else if(equals(status,'4')){
                return "事件联动结果更新"
            }else if(equals(status,'5')){
                return "事件图片异步上传"
            }
            return "-"
        },
        /**时间转换*/
        momentFormat(time) {
            if (isEmpty(time)) {
                return "";
            }
            return this.$moment(time).format("YYYY-MM-DD HH:mm:ss");
        },
        /**查看*/
        handleView(row) {
            const warnId = row.warnId
            selectEventById(warnId).then(response => {
                this.form = response.data;
                this.showDetail = true;
                console.log(this.form)
                if(!equals(this.acsEvent.indexOf(row.eventType),-1)){
                    this.title = "门禁事件";
                    this.acsShow = true;
                } else if(!equals(this.visEvent.indexOf(row.eventType),-1)){
                    this.title = "可视对讲事件";
                    this.visShow = true;
                } else if(!equals(this.fireEvent.indexOf(row.eventType),-1)){
                    this.title = "烟火事件";
                    this.fireShow = true;
                } else if(!equals(this.videoEvent.indexOf(row.eventType),-1)){
                    this.title = "监控事件";
                    this.videoShow = true;
                }
            });
        },
        // 取消按钮
        cancel() {
            this.showDetail =false;
            this.videoShow=false;
            this.visShow=false;
            this.acsShow=false;
            this.fireShow=false;
            this.reset();
        },
        // 表单重置
        reset() {
            this.resetForm("form");
        },
    }
}
</script>

<style scoped>

</style>
