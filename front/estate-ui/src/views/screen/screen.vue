<template>
  <div class="BGO1" :style="this.communityId == 1 ? this.bj01 : this.bj02">
    <div class="BG_MAX">
      <!-- title行 -->
      <el-row>
        <el-col :span="18">
          <el-row
            class="residential white"
            style="font-size: 1.3rem; font-weight: bold"
          >
            <el-dropdown @command="dropdownItem">
              <span
                class="el-dropdown-link"
                style="font-size: 1.5rem; color: #51c3d3"
              >
                {{ communityName
                }}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item
                  :command="
                    composeValue(i.deptId, i.communityId, i.communityName)
                  "
                  v-for="(i, j) in communityList"
                  :key="j"
                  >{{ i.communityName }}</el-dropdown-item
                >
              </el-dropdown-menu>
            </el-dropdown>
          </el-row>
        </el-col>
        <!--lingdang-->
        <el-col :span="1" style="font-size: 30px; text-align: right">
          <el-dropdown
            class="avatar-container right-menu-item hover-effect"
            trigger="click"
          >
            <el-badge
              :value="noticeList.length"
              :max="99"
              class="notice-badge"
              type="success"
            >
              <svg-icon
                icon-class="red-bell"
                class="notice-bell"
                style="font-size: 2rem"
              />
            </el-badge>
            <el-dropdown-menu
              slot="dropdown"
              class="notice-dropdown-menu"
              style="background-color: rgba(29, 86, 110, 0.8); border: 0"
            >
              <el-row>
                <label
                  class="el-form-item__label"
                  style="line-height: 3rem; margin-left: 10px; color: #cefffd"
                >
                  <svg-icon icon-class="vertical" />预警列表
                </label>
              </el-row>
              <h4
                class="form-header"
                style="
                  margin: 0 10px 10px 10px;
                  border-bottom: 1px solid #01d9d1;
                "
              ></h4>
              <el-row>
                <ul class="notice-list" style="list-style: none; width: 25rem">
                  <li
                    v-for="(notice, index) in noticeList"
                    style="margin-bottom: 15px; cursor: pointer"
                  >
                    <el-row style="line-height: 2rem">
                      <el-col :span="1" style="margin-left: -1.5rem">
                        <svg-icon icon-class="bell" />
                      </el-col>
                      <el-col
                        :span="12"
                        style="
                          white-space: nowrap;
                          overflow: hidden;
                          text-overflow: ellipsis;
                          font-size: 1rem;
                          font-weight: bold;
                          color: #cefffd;
                        "
                      >
                        <span>{{ notice.srcName }}</span>
                      </el-col>
                      <el-col :span="12">
                        <span style="color: #cefffd; font-size: 0.8rem">{{
                          parseTime(notice.recordTime)
                        }}</span>
                      </el-col>
                    </el-row>
                  </li>
                </ul>
              </el-row>
            </el-dropdown-menu>
          </el-dropdown>
        </el-col>

        <el-col :span="5">
          <el-row
            class="time white t0"
            style="color: #1890ff; text-align: center"
          >
            {{ getWeek }} {{ time.substring(0, 10) }}
            <span style="font-size: 2rem; margin-left: 3rem; color: #cefffd">{{
              time.substring(11, 20)
            }}</span>
          </el-row>
        </el-col>
      </el-row>
      <!-- 第二行  访客统计-->
      <el-row style="margin-top: 1.5rem; height: 22rem">
        <el-col :span="6" :offset="0" style="margin-left: 2rem" class="white">
          <el-row style="margin-left: 16rem; margin-top: 2.8rem; display: flex">
            <el-select
              v-model="cur"
              placeholder="请选择"
              style="width: 50%; margin-left: 80px"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <!--            <div class="item_01" :class="{ sut: cur == 0 }" @click="cur = 0">
              日
            </div>
            <div class="item_01" :class="{ sut: cur == 1 }" @click="cur = 1">
              月
            </div>
            <div class="item_01" :class="{ sut: cur == 2 }" @click="cur = 2">
              年
            </div>-->
            <!--            <div class="item_01" :class="{ sut: cur == 3 }" @click="cur = 3">
              累 计
            </div>-->
          </el-row>
          <el-row class="number_q" style="position: relative">
            <el-col :span="24" style="font-size: 4rem">
              {{
                cur == "0"
                  ? todayVisit
                  : cur == "1"
                  ? monthVisit
                  : cur == "2"
                  ? yearVisit
                  : totalVisit
              }}
            </el-col>
            <div
              style="
                position: absolute;
                top: -2rem;
                left: 15rem;
                color: white;
                letter-spacing: 0.6rem;
              "
            >
              {{ cur == 0 ? "日" : cur == 1 ? "月" : cur == 2 ? "年" : "累计" }}
              访客数
            </div>
          </el-row>
          <el-row>
            <el-col style="margin-top: 0.15rem">
              <el-row>
                <el-col :span="9" :offset="6" style="color: #00f6ec">{{
                  weekVisit
                }}</el-col>
                <el-col :span="4" :offset="5" style="color: #00f6ec"
                  >{{ weekRatio * 100 }}%</el-col
                >
              </el-row>
              <el-row style="margin-top: 1.5rem">
                <el-col :span="9" :offset="6" style="color: #f0ae45">{{
                  monthVisit
                }}</el-col>
                <el-col :span="4" :offset="5" style="color: #f0ae45"
                  >{{ monthRatio * 100 }}%</el-col
                >
              </el-row>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="5" :offset="6">
          <el-row class="white">
            <iframe
              width="360"
              scrolling="no"
              height="24"
              frameborder="0"
              style="background: rgba(0, 1, 3, 0.3)"
              allowtransparency="true"
              src="https://i.tianqi.com?c=code&id=40&color=%2300B0F0&bdc=%23&icon=1&py=xuancheng&site=12"
            ></iframe>
          </el-row>
        </el-col>
        <!-- main -->
        <el-col
          :span="6"
          :offset="0"
          style="display: flex; justify-content: space-between"
        >
          <el-row>
            <el-col
              class="white"
              style="height: 18rem; width: 18rem; margin: 4rem 0 0 1rem"
              ><div ref="main" style="width: 100%; height: 100%"></div
            ></el-col>
          </el-row>
          <el-row style="display: flex; color: white">
            <el-col style="line-height: 2rem" class="eys01">
              <el-row
                v-for="i in yearList"
                :key="i.id"
                style="padding: 0 0.4rem"
                >{{ i }}</el-row
              >
            </el-col>
            <el-col
              style="line-height: 2rem; width: 3.6rem; text-align: center"
              class="eys02"
            >
              <el-row
                class="index_01"
                style="line-height: 1.3rem; margin-bottom: 0.75rem"
                >男性</el-row
              >
              <el-row class="">{{ zeroToTenMan }}</el-row>
              <el-row class="">{{ elevenToTwentyMan }}</el-row>
              <el-row class="">{{ twentyOneToThirtyMan }}</el-row>
              <el-row class="">{{ thirtyOnetoFortyMan }}</el-row>
              <el-row class="">{{ fortyOneToFiftyMan }}</el-row>
              <el-row class="">{{ fiftyOneToSixtyMan }}</el-row>
              <el-row class="">{{ sixtyOneToSeventyMan }}</el-row>
              <el-row class="">{{ overSeventyMan }}</el-row>
            </el-col>
            <el-col
              style="line-height: 2rem; width: 3.6rem; text-align: center"
              class="eys03"
            >
              <el-row
                style="
                  background: #1682e5;
                  line-height: 1.3rem;
                  margin-bottom: 0.75rem;
                "
                >女性</el-row
              >
              <el-row class="">{{ zeroToTenWoman }}</el-row>
              <el-row class="">{{ elevenToTwentyWoman }}</el-row>
              <el-row class="">{{ twentyOneToThirtyWoman }}</el-row>
              <el-row class="">{{ thirtyOnetoFortyWoman }}</el-row>
              <el-row class="">{{ fortyOneToFiftyWoman }}</el-row>
              <el-row class="">{{ fiftyOneToSixtyWoman }}</el-row>
              <el-row class="">{{ sixtyOneToSeventyWoman }}</el-row>
              <el-row class="">{{ overSeventyWoman }}</el-row>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <!-- 第三行 初入详细数据分析-->
      <el-row style="color: white">
        <el-col :span="6" style="height: 15rem; margin-top: 2rem">
          <el-row>
            <!-- 数据分析1-->
            <el-col
              :span="12"
              style="height: 16rem; width: 16rem; margin: 0rem 0 0 0rem"
            >
              <div ref="angle" style="width: 100%; height: 100%"></div>
              <div style="margin-left: 5.8rem">
                <div>
                  当月出园量<span style="color: #25dbc9; margin: 0 0.5rem">{{
                    thisMonthVisitor
                  }}</span
                  >次
                </div>
                <div>
                  当月入园量<span style="color: #25dbc9; margin: 0 0.5rem"
                    >{{ thisMonthTotal }} </span
                  >次
                </div>
              </div>
            </el-col>
            <!-- 数据分析2-->
            <el-col
              :span="12"
              style="height: 16rem; width: 16rem; margin: 0rem 0 0 0rem"
            >
              <div ref="lineOne" style="width: 125%; height: 90%"></div>
              <div style="margin: 1.5rem 0 0 5.5rem">
                <div>
                  当月人行<span style="color: #f7746e; margin: 0 0.5rem">{{
                    thisMonthUser
                  }}</span
                  >次
                </div>
                <div>
                  当月车行<span style="color: #f7746e; margin: 0 0.5rem"
                    >{{ thisMonthVehicle }} </span
                  >次
                </div>
              </div>
            </el-col>
          </el-row>
        </el-col>
        <!-- 小区环境监测-->
        <el-col
          :span="6"
          :offset="12"
          style="height: 12.5rem; width: 33rem; margin-top: 3.3rem"
        >
          <div ref="lineTwo" style="width: 100%; height: 100%"></div>
        </el-col>
      </el-row>
      <!-- 第四行 2021年月度消耗 -->
      <el-row class="white">
        <el-row
          style="
            margin: 4.85rem 0 0 3.5rem;
            font-size: 0.9rem;
            padding-top: 1.1rem;
          "
        >
          <el-col>{{ yearCurrent }}年月度资源消耗</el-col>
        </el-row>
        <el-col :span="6" class="environment">
          <!-- 月 -->
          <div class="number" style="">
            <el-select
              v-model="typeName"
              placeholder="请选择"
              @change="getTypeChoose()"
              :popper-append-to-body="false"
            >
              <el-option
                v-for="item in typeList"
                :key="item.dictValue"
                :label="item.dictLabel"
                :value="item.dictValue"
              >
              </el-option>
            </el-select>
          </div>
          <div class="typeItemBox">
            <div class="list">
              <div
                v-for="(item, index) in typeItemList"
                :key="index"
                class="item"
                :class="item.dictValue == typeItemName ? 'active' : ''"
                @click="selectTypeItem(item)"
              >
                {{ item.dictLabel }}
              </div>
            </div>
          </div>
          <div
            ref="repairAnnual1"
            style="width: 100%; height: 22rem; margin-top: 2.9rem"
          ></div>
          <!-- <el-row
            style="margin: 1.15rem 0 0 0rem"
            v-for="i in consumeList"
            :key="i.id"
          >
            <el-col :span="23" style="margin-left: 2.4rem">
              <el-row>
                <el-col
                  :span="2"
                  style="text-align: right; margin-left: -1.1rem"
                  >{{ i.month }}</el-col
                >
                <el-col :span="5" style="margin-left: 1.8rem">
                  <el-row>
                    <el-col :span="24" class="t0 Rg">
                      <el-row>
                        <el-col :span="5">{{ i.ec }}</el-col>
                        <el-col :span="7" class="Cr">/200</el-col>
                      </el-row>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="16">
                      <el-progress
                        :text-inside="true"
                        :percentage="i.ec / 2"
                      ></el-progress>
                    </el-col>
                  </el-row>
                </el-col>
                <el-col :span="5" style="margin-left: 4.1rem">
                  <el-row>
                    <el-col :span="24" class="t0 Rg">
                      <el-row>
                        <el-col :span="5">{{ i.hg }}</el-col>
                        <el-col :span="7" class="Cr">/200</el-col>
                      </el-row>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="16">
                      <el-progress
                        :text-inside="true"
                        :percentage="i.hg / 2"
                      ></el-progress>
                    </el-col>
                  </el-row>
                </el-col>
                <el-col :span="5" style="margin-left: 3.9rem">
                  <el-row>
                    <el-col :span="24" class="t0 Rg">
                      <el-row>
                        <el-col :span="5">{{ i.cr }}</el-col>
                        <el-col :span="7" class="Cr">/200</el-col>
                      </el-row>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="16">
                      <el-progress
                        :text-inside="true"
                        :percentage="i.cr / 2"
                      ></el-progress>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </el-col>
          </el-row> -->
        </el-col>
        <!-- 社区信息1-->

        <el-col :span="5" style="margin: 0rem 0 0 4rem">
          <el-row style="margin: -1.1rem 0 0 1rem; font-size: 0.9rem">
            <el-col>{{ yearCurrent }}年{{ communityName }}小区信息</el-col>
          </el-row>

          <el-row
            style="border: 0.1rem solid #459380; margin: 1.9rem 0 0 0.5rem"
          >
            <el-col
              :span="6"
              v-for="i in titleList"
              :key="i.id"
              style="
                text-align: center;
                padding: 0.2rem 0;
                background: rgba(37, 113, 128, 0.5);
              "
              >{{ i }}</el-col
            >
          </el-row>
          <el-row
            style="
              text-align: center;
              border-bottom: 0.2rem #459380 dotted;
              padding: 0.5rem 0;
            "
          >
            <el-col :span="6">门禁设备</el-col>
            <el-col :span="6" style="color: #e0d969">{{ accessOnLine }}</el-col>
            <el-col :span="6" style="color: #2deafa">{{
              accessOffLine
            }}</el-col>
            <el-col :span="6">
              {{ proportionOne }} %
              <!-- <el-row style="margin-left: 1.3rem">
                <el-col :span="15"
                  ><el-slider v-model=""></el-slider
                ></el-col>
                <el-col
                  :span="6"
                  style="font-size: 0.8rem; padding-top: 0.25rem"
                  >0%</el-col
                >
              </el-row> -->
            </el-col>
          </el-row>
          <el-row
            style="
              text-align: center;
              border-bottom: 0.2rem #459380 dotted;
              padding: 0.5rem 0;
            "
          >
            <el-col :span="6">监控设备</el-col>
            <el-col :span="6" style="color: #e0d969">{{
              monitorOnLine
            }}</el-col>
            <el-col :span="6" style="color: #2deafa">{{
              monitorOffLine
            }}</el-col>
            <el-col :span="6">
              {{ proportionTwo }} %
              <!-- <el-row style="margin-left: 1.3rem">
                <el-col :span="15"
                  ><el-slider v-model=""></el-slider
                ></el-col>
                <el-col
                  :span="6"
                  style="font-size: 0.8rem; padding-top: 0.25rem"
                  >0%</el-col
                >
              </el-row> -->
            </el-col>
          </el-row>
          <el-row
            style="
              text-align: center;
              border-bottom: 0.2rem #459380 dotted;
              padding: 0.5rem 0;
            "
          >
            <el-col :span="6">抓拍显示一体机</el-col>
            <el-col :span="6" style="color: #e0d969">{{ aioonLine }}</el-col>
            <el-col :span="6" style="color: #2deafa">{{ aiooffLine }}</el-col>
            <el-col :span="6">
              {{ proportionTherr }} %
              <!-- <el-row style="margin-left: 1.3rem">
                <el-col :span="15"
                  ><el-slider v-model=""></el-slider
                ></el-col>
                <el-col
                  :span="6"
                  style="font-size: 0.8rem; padding-top: 0.25rem"
                  >0%</el-col
                >
              </el-row> -->
            </el-col>
          </el-row>
        </el-col>
        <!-- 社区信息2-->
        <el-col :span="6" style="margin-left: 1.8rem">
          <el-row>
            <el-col
              :span="12"
              style="height: 18rem; width: 16rem; margin: 0rem 0 0 0rem"
            >
              <div
                ref="cakeTwo"
                style="width: 100%; height: 100%; margin-top: 0rem"
              ></div>
            </el-col>
            <el-col :span="12" style="margin: 1.5rem 0 0 1rem">
              <el-row style="margin-top: 0rem">
                <el-col :span="10">保洁人员</el-col>
                <el-col
                  :span="8"
                  style="
                    background: #1fab99;
                    padding: 0.1rem 0;
                    text-align: center;
                  "
                  >{{ staffProperty }}</el-col
                >
                <el-col :span="1" :offset="1">人</el-col>
              </el-row>
              <el-row style="margin-top: 0.8rem">
                <el-col :span="10">维修人员</el-col>
                <el-col
                  :span="8"
                  style="
                    background: #f7746e;
                    padding: 0.1rem 0;
                    text-align: center;
                  "
                  >{{ staffRepair }}</el-col
                >
                <el-col :span="1" :offset="1">人</el-col>
              </el-row>
              <el-row style="margin-top: 0.8rem">
                <el-col :span="10">安保人员</el-col>
                <el-col
                  :span="8"
                  style="
                    background: #17a3df;
                    padding: 0.1rem 0;
                    text-align: center;
                  "
                  >{{ staffSecurity }}</el-col
                >
                <el-col :span="1" :offset="1">人</el-col>
              </el-row>
              <!-- <el-row style="margin-top: 0.8rem">
                <el-col :span="10">机器满意度</el-col>
                <el-col :span="10" style="color: #10d0d6">忙碌中</el-col>
              </el-row> -->
              <el-row style="font-size: 1.1rem; margin-top: 1.8rem"
                >报事报修满意度调查：</el-row
              >
              <el-row style="text-align: center">
                <el-col :span="15">
                  <el-row
                    style="
                      background: #025f98;
                      height: 3rem;
                      padding-top: 0.61rem;
                    "
                    >很满意：{{ praise }}%</el-row
                  >
                  <el-row style="background: #08a94e; height: 1.5rem"
                    >不满意：{{ negative }}%</el-row
                  >
                </el-col>
                <el-col
                  :span="6"
                  style="background: #dcc600; height: 4.5rem; padding-top: 1rem"
                >
                  <el-row>未评价</el-row>
                  <el-row style="margin-top: 0.2rem"
                    >{{ notEvaluated }}%</el-row
                  >
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </el-col>
        <!-- 小区工单详情 -->
        <el-col
          :span="6"
          style="
            overflow: hidden;
            height: 26rem;
            padding-top: 3.3rem;
            margin-top: -5rem;
          "
          id="con1"
          ref="con1"
          :class="{ anim: animate == true }"
          class="anim"
        >
          <el-row
            style="
              margin-top: 0rem;
              color: #51c3d3;
              padding-bottom: 1rem;
              font-size: 0.8rem;
            "
            v-for="(k, i) in deviceList"
            :key="i.id"
          >
            <el-col :span="4" style="padding-left: 1rem; font-size: 0.8rem">{{
              k.orderNo == "0"
                ? "电梯"
                : k.orderNo == "1"
                ? "排污泵"
                : k.orderNo == "2"
                ? "高压电房"
                : k.orderNo == "3"
                ? "低压电房"
                : k.orderNo == "4"
                ? "灭火器"
                : k.orderNo == "5"
                ? "消防栓"
                : k.orderNo == "6"
                ? "风机房"
                : k.orderNo == "7"
                ? "管道进"
                : k.orderNo == "8"
                ? "消防泵房"
                : k.orderNo == "9"
                ? "监控室"
                : k.orderNo == "10"
                ? "智能化设备"
                : k.orderNo == "11"
                ? "消火栓"
                : k.orderNo == "12"
                ? "消防器"
                : "其他"
            }}</el-col>
            <el-col :span="10" style="text-align: left; margin-left: 2.5rem">{{
              k.content == "" ? "暂无" : k.content
            }}</el-col>
            <el-col :span="3" style="margin-left: 0rem">{{
              k.staffName
            }}</el-col>
            <el-col
              :span="4"
              style="text-align: center; margin-left: 1rem"
              :style="{
                color:
                  k.patrolStatus == 0
                    ? '#126CBF'
                    : k.patrolStatus == 1
                    ? '#1AA034'
                    : k.patrolStatus == 2
                    ? '#BF3636'
                    : '#BF3636',
              }"
            >
              {{
                k.patrolStatus == 0
                  ? "待上报"
                  : k.patrolStatus == 1
                  ? "已完成"
                  : k.patrolStatus == 2
                  ? "已超期"
                  : "已超期"
              }}
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import { getCurrentList } from "@/api/estate/community";
import {
  getVisitLogCount,
  getDmyVisitLogCount,
  getPeopleInAndOutChart,
  getVisitCharts,
  getEquipmentCount,
  getOwnerAge,
  getStaffCount,
  getPatrolOrder,
  getInAndOut,
  getEvaluate,
  listWaring,
  getdictData,
  selectWaterNum,
} from "@/api/screen/screen";
export default {
  name: "Screen",
  data() {
    return {
      noticeList: [],
      yearCurrent: "",
      animate: false,
      inOutPieChart: null,
      thisMonthVisitor: 0,
      thisMonthTotal: 0,
      thisMonthUser: 0,
      thisMonthVehicle: 0,
      pieList: [],
      inOutLineChart: null,
      thisYearUser: [],
      thisYearVehicle: [],
      num_one: "1224",
      num_two: "2226",
      cur: "0",
      titleList: ["事务类型", "在线数量", "离线数量", "占比"],
      yearList: [
        "0-10岁",
        "11-20岁",
        "21-30岁",
        "31-40岁",
        "41-50岁",
        "51-60岁",
        "61-70岁",
        "70岁以上",
      ],
      consumeList: [
        { month: "八月", ec: "140", hg: "150", cr: "150", gs: "150" },
        { month: "七月", ec: "130", hg: "110", cr: "150", gs: "180" },
        { month: "六月", ec: "140", hg: "110", cr: "150", gs: "180" },
        { month: "五月", ec: "160", hg: "110", cr: "120", gs: "150" },
      ],
      options: [
        { value: "0", label: "日" },
        { value: "1", label: "月" },
        { value: "2", label: "年" },
      ],
      communityList: [],
      communityName: "",
      communityId: "",
      deptId: "",
      todayVisit: "",
      weekVisit: "",
      monthVisit: "",
      yearVisit: "",
      totalVisit: "",
      monthRatio: "",
      weekRatio: "",
      accessOnLine: "",
      accessOffLine: "",
      monitorOffLine: "",
      monitorOnLine: "",
      aioonLine: "",
      aiooffLine: "",
      staffRepair: "",
      staffProperty: "",
      staffSecurity: "",
      zeroToTenMan: "",
      zeroToTenWoman: "",
      elevenToTwentyMan: "",
      elevenToTwentyWoman: "",
      twentyOneToThirtyMan: "",
      twentyOneToThirtyWoman: "",
      thirtyOnetoFortyMan: "",
      thirtyOnetoFortyWoman: "",
      fortyOneToFiftyMan: "",
      fortyOneToFiftyWoman: "",
      fiftyOneToSixtyMan: "",
      fiftyOneToSixtyWoman: "",
      sixtyOneToSeventyMan: "",
      sixtyOneToSeventyWoman: "",
      overSeventyMan: "",
      overSeventyWoman: "",
      addManData: "",
      addWomanData: "",
      deviceList: [],
      time: "",
      getWeek: "",
      bj01: {
        backgroundImage: "url(" + require("../../assets/large/wy.png") + ")",
      },
      bj02: {
        backgroundImage: "url(" + require("../../assets/large/wy.png") + ")",
      },
      proportionOne: "",
      proportionTwo: "",
      proportionTherr: "",
      listAndOutIn: [],
      listAndOutOut: [],
      praise: "",
      notEvaluated: "",
      negative: "",
      typeList: [],
      typeName: "",
      typeItemName: "",
      valueSmart: 1,
      typeItemList: [],
      EnvironmentalTesting: [],
      maxTen: "",
    };
  },
  created() {
    this.viewNoticeList();
    function placeholderPic() {
      var w = document.documentElement.offsetWidth;
      document.documentElement.style.fontSize = w / 140 + "px";
    }
    placeholderPic();
    window.onresize = function () {
      placeholderPic();
    };
  },
  mounted() {
    this.getdictData();
    this.getCommunityList();
    setInterval(() => {
      this.viewNoticeList();
    }, 60000);
    this.intChart();
    setInterval(() => {
      this.newTime();
    }, 900);
  },
  methods: {
    getdictData() {
      getdictData({ dictType: "energy_consumption_type" }).then((res) => {
        this.typeList = res.data;
        this.typeName = res.data[0].dictValue;
        getdictData({
          dictType: "meter_reading_type",
          type: this.typeName,
        }).then((res) => {
          this.typeItemList = res.data;
          this.typeItemName = res.data[0].dictValue;

          // 水电表
          this.selectWaterNum();
        });
      });
    },
    getTypeChoose() {
      getdictData({
        dictType: "meter_reading_type",
        type: this.typeName,
      }).then((res) => {
        this.typeItemList = res.data;
        this.typeItemName = res.data[0].dictValue;
        // 水电表
        this.selectWaterNum();
      });
    },
    selectTypeItem(item) {
      this.typeItemName = item.dictValue;
      this.selectWaterNum();
    },
    // 环境检测
    selectWaterNum() {
      selectWaterNum({
        energyConsumptionType: this.typeName,
        meterReadingType: this.typeItemName,
        year: new Date().getFullYear(),
        communityId: this.communityId,
      }).then((res) => {
        this.EnvironmentalTesting = [];
        // this.EnvironmentalTesting = res.data;
        this.maxTen = 0;
        res.data.forEach((item, index) => {
          this.EnvironmentalTesting.push(item);
          if (item > this.maxTen) {
            this.maxTen = item;
          }
        });
        this.maintenanceAnnual1();
      });
    },
    // 环境检测
    maintenanceAnnual1() {
      let myChart = echarts.init(this.$refs.repairAnnual1);
      var xData = [
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12",
      ];
      var line = ["0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"];
      line = this.EnvironmentalTesting;
      var graphicData = ["单位：吨"];
      let repairAnnual = {
        tooltip: {
          show: true,
          trigger: "item",
          formatter: "{b}月:{c}吨",
        },
        graphic: [
          {
            type: "text",
            z: 100,
            left: "6%",
            top: "6%",
            style: {
              fill: "#81A5E8",
              text: graphicData.join("\n"),
              // padding: [20, 20],
              // margin: [10, 10],
              font: "italic small-caps  18px/2 cursive",
              fontSize: 10,
            },
          },
        ],
        grid: {
          left: "10%",
          top: "18%",
          right: "5%",
          bottom: "15%",
        },
        legend: {
          show: true,
          icon: "circle",
          orient: "horizontal",
          top: "90.5%",
          right: "center",
          itemWidth: 16.5,
          itemHeight: 6,
          textStyle: {
            color: "#C9C8CD",
            fontSize: 10,
          },
        },
        xAxis: [
          {
            data: xData,
            axisLabel: {
              textStyle: {
                color: "#aaaaaa",
                fontSize: 10,
              },
              margin: 10, //刻度标签与轴线之间的距离。
            },

            axisLine: {
              show: false, //不显示x轴
            },
            axisTick: {
              show: false, //不显示刻度
            },
            boundaryGap: true,
            splitLine: {
              show: false,
              width: 0.08,
              lineStyle: {
                type: "solid",
                color: "#03202E",
              },
            },
          },
        ],
        yAxis: [
          {
            min: 0,
            max: 100,
            splitLine: {
              show: true,
              lineStyle: {
                color: "#174b6a",
                type: "dashed",
              },
            },
            axisTick: {
              show: true,
            },
            axisLine: {
              show: false,
            },
            axisLabel: {
              textStyle: {
                color: "#888",
                fontSize: 10,
              },
            },
          },
        ],
        series: [
          {
            name: "",
            type: "bar",
            zlevel: 1,
            showBackground: true,
            itemStyle: {
              normal: {
                backgroundStyle: {
                  color: "rgba(159, 177, 183,0.2)",
                },
                color: new echarts.graphic.LinearGradient(
                  0,
                  1,
                  0,
                  0,

                  [
                    {
                      offset: 0,
                      color: "rgba(67, 235, 237, 0.24)",
                    },
                    {
                      offset: 1,
                      color: "rgba(70, 249, 249, 0.88)",
                    },
                  ],
                  false
                ),
              },
            },
            barWidth: 16,
            data: line,
          },
        ],
      };
      repairAnnual.yAxis[0].max = this.maxTen;
      myChart.setOption(repairAnnual);
      this.dynamic(myChart, 12);
    },
    viewNoticeList() {
      listWaring().then((response) => {
        this.noticeList = response.data.slice(0, 9);
      });
    },
    newTime() {
      const d = new Date();
      const y = d.getFullYear();
      this.yearCurrent = y;
      const m = (d.getMonth() + 1 + "").padStart(2, "0");
      const dt = (d.getDate() + "").padStart(2, "0");
      const h = (d.getHours() + "").padStart(2, "0");
      const mm = (d.getMinutes() + "").padStart(2, "0");
      const s = (d.getSeconds() + "").padStart(2, "0");
      const w = new Date().getDay();
      const week = ["日", "一", "二", "三", "四", "五", "六"];
      this.getWeek = "星期" + week[w];
      return (this.time = `${y}-${m}-${dt} ${h}:${mm}:${s}`);
    },
    getInAndOut() {
      let params = { communityId: this.communityId };
      getInAndOut(params).then((res) => {
        let array = res.inLog;
        let array1 = res.outLog;
        this.listAndOutIn = Object.values(array);
        this.listAndOutOut = Object.values(array1);
        this.brokenLineTwo();
        this.getEvaluate();
      });
    },
    scroll() {
      this.animate = true;
      var that = this; // 在异步函数中会出现this的偏移问题，此处一定要先保存好this的指向
      setTimeout(function () {
        that.deviceList.push(that.deviceList[0]);
        that.deviceList.shift();
        that.animate = false;
      }, 500);
    },
    getEvaluate() {
      let param = { communityId: this.communityId };
      getEvaluate(param).then((res) => {
        let kp = res.praise;
        let kt = res.total;
        let kn = res.notEvaluated;
        let kl = res.negative;
        if (kt > 0) {
          this.praise = ((kp / kt) * 100).toFixed(0);
          this.notEvaluated = ((kn / kt) * 100).toFixed(0);
          this.negative = ((kl / kt) * 100).toFixed(0);
        } else {
          this.praise = 0;
          this.notEvaluated = 0;
          this.negative = 0;
        }
      });
    },
    // //查询小区列表
    getCommunityList() {
      getCurrentList().then((res) => {
        let k = res.data;
        this.communityList = k;
        this.communityName = k[0].communityName;
        this.communityId = k[0].communityId;
        this.deptId = k[0].estateId;
        this.getVisitLogCount();
        this.getInOutChart();
        this.getOwnerAge();
        this.getEquipmentCount();
        this.getStaffCount();
        this.getPatrolOrder();
        this.getInAndOut();
        this.getEvaluate();
      });
    },
    //访客统计
    getVisitLogCount() {
      let param = { communityId: this.communityId };
      getVisitLogCount(param).then((res) => {
        this.weekVisit = res.thisWeekNum;
        this.weekRatio = res.weekRate;
        this.monthVisit = res.thisMonthNum;
        this.monthRatio = res.monthRate;
      });
      getDmyVisitLogCount(param).then((res) => {
        this.todayVisit = res.dayVisitorNum;
        this.monthVisit = res.monthVisitorNum;
        this.yearVisit = res.yearVisitorNum;
      });
    },
    //出入详细数据分析
    getInOutChart() {
      let param = { communityId: this.communityId };
      getPeopleInAndOutChart(param).then((res) => {
        this.thisMonthVisitor = res.data.outNum;
        // this.thisMonthTotal = res.data.visit + res.data.owner + res.data.tenant;
        this.thisMonthTotal = res.data.inNum;
        this.pieList = [
          { name: "进门", icon: "circle", value: res.data.inNum },
          { name: "出门", icon: "circle", value: res.data.outNum },
          // { name: "访客", icon: "circle", value: res.data.visit },
        ];
        this.setInOutPieOptions();
      });
      getVisitCharts(param).then((res) => {
        this.thisMonthUser = res.thisMonthUserNum;
        this.thisMonthVehicle = res.thisMonthVehicleNum;
        this.thisYearUser = res.userChart;
        this.thisYearVehicle = res.vehicleChart;
        // this.thisYearUser.push(res.userChart.january);
        // this.thisYearUser.push(res.userChart.february);
        // this.thisYearUser.push(res.userChart.march);
        // this.thisYearUser.push(res.userChart.april);
        // this.thisYearUser.push(res.userChart.may);
        // this.thisYearUser.push(res.userChart.june);
        // this.thisYearUser.push(res.userChart.july);
        // this.thisYearUser.push(res.userChart.august);
        // this.thisYearUser.push(res.userChart.september);
        // this.thisYearUser.push(res.userChart.october);
        // this.thisYearUser.push(res.userChart.november);
        // this.thisYearUser.push(res.userChart.december);
        // this.thisYearVehicle.push(res.vehicleChart.january);
        // this.thisYearVehicle.push(res.vehicleChart.february);
        // this.thisYearVehicle.push(res.vehicleChart.march);
        // this.thisYearVehicle.push(res.vehicleChart.april);
        // this.thisYearVehicle.push(res.vehicleChart.may);
        // this.thisYearVehicle.push(res.vehicleChart.june);
        // this.thisYearVehicle.push(res.vehicleChart.july);
        // this.thisYearVehicle.push(res.vehicleChart.august);
        // this.thisYearVehicle.push(res.vehicleChart.september);
        // this.thisYearVehicle.push(res.vehicleChart.october);
        // this.thisYearVehicle.push(res.vehicleChart.november);
        // this.thisYearVehicle.push(res.vehicleChart.december);
        this.setInOutLineOptions();
      });
    },
    getEquipmentCount() {
      //设备在线离线数量统计
      let params = { communityId: this.communityId };
      getEquipmentCount(params).then((res) => {
        let k = res.data;
        this.accessOnLine = k.accessOnLine;
        this.accessOffLine = k.accessOffLine;
        let kk = Number(this.accessOnLine) + Number(this.accessOffLine);
        if (kk == 0) {
          this.proportionOne = Number(this.accessOnLine) / 1;
        } else {
          this.proportionOne = Number(this.accessOnLine) / kk;
        }
        this.proportionOne = (this.proportionOne * 100).toFixed(0);

        this.monitorOffLine = k.monitorOffLine;
        this.monitorOnLine = k.monitorOnLine;
        let nn = Number(this.monitorOffLine) + Number(this.monitorOnLine);
        if (nn == 0) {
          this.proportionTwo = Number(this.monitorOnLine) / 1;
        } else {
          this.proportionTwo = Number(this.monitorOnLine) / nn;
        }
        this.proportionTwo = (this.proportionTwo * 100).toFixed(0);

        this.aioonLine = k.aioonLine;
        this.aiooffLine = k.aiooffLine;
        let zz = Number(this.aioonLine) + Number(this.aiooffLine);
        if (zz == 0) {
          this.proportionTherr = Number(this.aioonLine) / 1;
        } else {
          this.proportionTherr = Number(this.aioonLine) / zz;
        }
        this.proportionTherr = (this.proportionTherr * 100).toFixed(0);
      });
    },
    getOwnerAge() {
      //获取年龄段人数
      let params = { communityId: this.communityId };
      getOwnerAge(params).then((res) => {
        let k = res.data;
        this.zeroToTenMan = k.zeroToTenMan; //0-10岁男
        this.elevenToTwentyMan = k.elevenToTwentyMan; //11-20男
        this.twentyOneToThirtyMan = k.twentyOneToThirtyMan; //21-30男性
        this.thirtyOnetoFortyMan = k.thirtyOnetoFortyMan; //31-40男性
        this.fortyOneToFiftyMan = k.fortyOneToFiftyMan; //41-50男
        this.fiftyOneToSixtyMan = k.fiftyOneToSixtyMan; //51-60男性
        this.sixtyOneToSeventyMan = k.sixtyOneToSeventyMan; //61-70男
        this.overSeventyMan = k.overSeventyMan; //61-70男
        this.addManData =
          this.zeroToTenMan +
          "," +
          this.elevenToTwentyMan +
          "," +
          this.twentyOneToThirtyMan +
          "," +
          this.thirtyOnetoFortyMan +
          "," +
          this.fortyOneToFiftyMan +
          "," +
          this.fiftyOneToSixtyMan +
          "," +
          this.sixtyOneToSeventyMan +
          "," +
          this.overSeventyMan;

        this.zeroToTenWoman = k.zeroToTenWoman; //0-10岁
        this.elevenToTwentyWoman = k.elevenToTwentyWoman; //11-20
        this.twentyOneToThirtyWoman = k.twentyOneToThirtyWoman; //21-30
        this.thirtyOnetoFortyWoman = k.thirtyOnetoFortyWoman; //31-40
        this.fortyOneToFiftyWoman = k.fortyOneToFiftyWoman; //41-50
        this.fiftyOneToSixtyWoman = k.fiftyOneToSixtyWoman; //51-60
        this.sixtyOneToSeventyWoman = k.sixtyOneToSeventyWoman; //61-70
        this.overSeventyWoman = k.overSeventyWoman; //61-70
        this.addWomanData =
          this.zeroToTenWoman +
          "," +
          this.elevenToTwentyWoman +
          "," +
          this.twentyOneToThirtyWoman +
          "," +
          this.thirtyOnetoFortyWoman +
          "," +
          this.fortyOneToFiftyWoman +
          "," +
          this.fiftyOneToSixtyMan +
          "," +
          this.sixtyOneToSeventyWoman +
          "," +
          this.overSeventyWoman;
        this.diagramNetwork();
      });
    },
    getStaffCount() {
      //员工数据统计
      let params = { deptId: this.communityId };
      getStaffCount(params).then((res) => {
        let k = res.data;
        this.staffRepair = k.staffRepair; //维修人员数
        this.staffProperty = k.staffProperty; //安防人员数
        this.staffSecurity = k.staffSecurity; //物业管理人员
        this.cakeTwo();
      });
    },
    getPatrolOrder() {
      //小区工单
      let params = { communityId: this.communityId };
      getPatrolOrder(params).then((res) => {
        this.deviceList = res.data;
        setInterval(this.scroll, 1000);
      });
    },
    composeValue(d, c, n) {
      return {
        d: d,
        c: c,
        n: n,
      };
    },
    dropdownItem(e) {
      this.communityName = e.n;
      this.communityId = e.c;
      this.deptId = e.c;
      this.getVisitLogCount();
      this.getOwnerAge();
      this.getEquipmentCount();
      this.getStaffCount();
      this.getPatrolOrder();
      this.getEvaluate();
      this.getInAndOut();
    },
    // 封装的echarts里data数据
    dynamic(echart, length) {
      //播放所在下标
      let index = 0;
      setInterval(function () {
        echart.dispatchAction({
          type: "showTip",
          seriesIndex: 0,
          dataIndex: index,
        });
        index++;
        if (index > length) {
          index = 0;
        }
      }, 1500);
    },
    // 小区业户人员组织图 雷达图
    diagramNetwork() {
      const myChart = echarts.init(this.$refs["main"]);
      let kk = 0;
      let man = this.addManData.split(",");
      //  男性数量
      man.forEach((e) => {
        if (kk < e) {
          kk = e;
        }
      });
      let woman = this.addWomanData.split(",");
      //  女性数量
      woman.forEach((e) => {
        if (kk < e) {
          kk = e;
        }
      });
      let option = {
        normal: {
          top: 0,
          left: 0,
          width: 0,
          height: 0,
          zIndex: 6,
          backgroundColor: "",
        },
        color: ["rgba(245, 166, 35, 1)", "rgba(19, 173, 255, 1)"],
        title: {
          show: true,
          text: "",
          left: "40%",
          top: "1%",
          textStyle: {
            fontSize: 8,
            color: "#fff",
            fontStyle: "normal",
            fontWeight: "normal",
          },
        },
        tooltip: {
          show: true,
          trigger: "item",
          textStyle: {
            fontSize: 10,
          },
        },
        legend: {
          show: true,
          icon: "circle",
          left: "35%",
          top: "90%",
          orient: "horizontal",
          textStyle: {
            fontSize: 8,
            color: "#fff",
          },
          data: ["男", "女"],
        },
        radar: {
          center: ["50%", "50%"],
          radius: "50%",
          startAngle: 70,
          splitNumber: 4,
          shape: "circle",
          splitArea: {
            areaStyle: {
              color: ["transparent"],
            },
          },
          name: {
            textStyle: {
              color: "#CEFFFD",
              fontSize: "11",
              borderRadius: 3,
              padding: [0, -10],
            },
          },
          axisLabel: {
            show: false,
            fontSize: 8,
            color: "#fff",
            fontStyle: "normal",
            fontWeight: "normal",
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: "#DAFFFE",
            },
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: "#DAFFFE",
            },
          },
          indicator: [
            {
              name: "0-10岁",
              max: 500,
            },
            {
              name: "11-20岁",
              max: 500,
            },
            {
              name: "21-30岁",
              max: 500,
            },
            {
              name: "31-40岁",
              max: 500,
            },
            {
              name: "41-50岁",
              max: 500,
            },
            {
              name: "51-60岁",
              max: 500,
            },

            {
              name: "61-70岁",
              max: 500,
            },
            {
              name: "70岁以上",
              max: 500,
            },
          ],
        },
        series: [
          {
            name: "男性",
            type: "radar",
            symbol: "circle",
            symbolSize: 0,
            areaStyle: {
              normal: {
                color: "rgba(5,208,201, 0.9)",
              },
            },
            itemStyle: {
              color: "rgba(245, 166, 35, 1)",
              borderColor: "rgba(5,208,201, 0.9)",
              borderWidth: 8,
            },
            lineStyle: {
              normal: {
                type: "solid",
                color: "rgba(8, 133, 144, 1)",
                width: 1,
              },
            },
            data: [man],
          },
          {
            name: "女性",
            type: "radar",
            symbol: "circle",
            symbolSize: 0,
            itemStyle: {
              normal: {
                color: "rgba(17, 193, 146, 1)",
                borderColor: "rgba(23,122,215, 0.4)",
                borderWidth: 8,
              },
            },
            areaStyle: {
              normal: {
                color: "rgba(23,122,215, 0.5)",
              },
            },
            lineStyle: {
              normal: {
                color: "rgba(23,122,215, 1)",
                width: 1,
                type: "solid",
              },
            },
            data: [woman],
          },
        ],
      };
      myChart.setOption(option);
    },

    setInOutPieOptions() {
      let that = this;
      let angle = 0; //角度，用来做简单的动画效果的
      let color = [
        "#01A75B",
        "#F29B14",
        "#007EFC",
        "#9F17FF",
        "#FFE400",
        "#F76F01",
        "#01A4F7",
        "#FE2C8A",
      ];
      let option = {
        legend: {
          type: "plain",
          right: 0,
          bottom: "0%",
          x: "center",
          data: that.pieList,
          itemWidth: 5,
          itemHeight: 5,
          itemGap: 10,
          icon: "roundRect",
          textStyle: {
            color: "#fff",
            padding: [10, 1, 10, 0],
          },
          selectedMode: false,
        },
        series: [
          {
            name: "ring5",
            type: "custom",
            coordinateSystem: "none",
            renderItem: function (params, api) {
              return {
                type: "arc",
                shape: {
                  cx: api.getWidth() / 2,
                  cy: api.getHeight() / 2,
                  r: (Math.min(api.getWidth(), api.getHeight()) / 2) * 0.6,
                  startAngle: ((0 + angle) * Math.PI) / 180,
                  endAngle: ((90 + angle) * Math.PI) / 180,
                },
                style: {
                  stroke: "#0CD3DB",
                  fill: "transparent",
                  lineWidth: 1.5,
                },
                silent: true,
              };
            },
            data: [0],
          },
          {
            name: "ring5",
            type: "custom",
            coordinateSystem: "none",
            renderItem: function (params, api) {
              return {
                type: "arc",
                shape: {
                  cx: api.getWidth() / 2,
                  cy: api.getHeight() / 2,
                  r: (Math.min(api.getWidth(), api.getHeight()) / 2) * 0.6,
                  startAngle: ((180 + angle) * Math.PI) / 180,
                  endAngle: ((270 + angle) * Math.PI) / 180,
                },
                style: {
                  stroke: "#0CD3DB",
                  fill: "transparent",
                  lineWidth: 1.5,
                },
                silent: true,
              };
            },
            data: [0],
          },
          {
            name: "ring5",
            type: "custom",
            coordinateSystem: "none",
            renderItem: function (params, api) {
              return {
                type: "arc",
                shape: {
                  cx: api.getWidth() / 2,
                  cy: api.getHeight() / 2,
                  r: (Math.min(api.getWidth(), api.getHeight()) / 2) * 0.65,
                  startAngle: ((270 + -angle) * Math.PI) / 180,
                  endAngle: ((40 + -angle) * Math.PI) / 180,
                },
                style: {
                  stroke: "#0CD3DB",
                  fill: "transparent",
                  lineWidth: 1.5,
                },
                silent: true,
              };
            },
            data: [0],
          },
          {
            name: "ring5",
            type: "custom",
            coordinateSystem: "none",
            renderItem: function (params, api) {
              return {
                type: "arc",
                shape: {
                  cx: api.getWidth() / 2,
                  cy: api.getHeight() / 2,
                  r: (Math.min(api.getWidth(), api.getHeight()) / 2) * 0.65,
                  startAngle: ((90 + -angle) * Math.PI) / 180,
                  endAngle: ((220 + -angle) * Math.PI) / 180,
                },
                style: {
                  stroke: "#0CD3DB",
                  fill: "transparent",
                  lineWidth: 1.5,
                },
                silent: true,
              };
            },
            data: [0],
          },
          {
            name: "ring5",
            type: "custom",
            coordinateSystem: "none",
            renderItem: function (params, api) {
              let x0 = api.getWidth() / 2;
              let y0 = api.getHeight() / 2;
              let r = (Math.min(api.getWidth(), api.getHeight()) / 2) * 0.65;
              let point = getCirlPoint(x0, y0, r, 90 + -angle);
              return {
                type: "circle",
                shape: {
                  cx: point.x,
                  cy: point.y,
                  r: 4,
                },
                style: {
                  stroke: "#0CD3DB", //粉
                  fill: "#0CD3DB",
                },
                silent: true,
              };
            },
            data: [0],
          },
          {
            name: "ring5", //绿点
            type: "custom",
            coordinateSystem: "none",
            renderItem: function (params, api) {
              let x0 = api.getWidth() / 2;
              let y0 = api.getHeight() / 2;
              let r = (Math.min(api.getWidth(), api.getHeight()) / 2) * 0.65;
              let point = getCirlPoint(x0, y0, r, 270 + -angle);
              return {
                type: "circle",
                shape: {
                  cx: point.x,
                  cy: point.y,
                  r: 4,
                },
                style: {
                  stroke: "#0CD3DB", //绿
                  fill: "#0CD3DB",
                },
                silent: true,
              };
            },
            data: [0],
          },
          {
            name: "吃猪肉频率",
            type: "pie",
            radius: ["50%", "30%"],
            silent: true,
            clockwise: true,
            startAngle: 180,
            z: 0,
            zlevel: 0,
            color: color,
            itemStyle: {
              normal: {
                borderColor: "rgba(255,255,255,0.1)",
                borderWidth: "5",
              },
            },
            label: {
              normal: {
                position: "inside",
                formatter: function (params) {
                  return params.percent + "%";
                },
              },
            },
            data: that.pieList,
          },
        ],
      };
      this.inOutPieChart.setOption(option);
      function dotArr() {
        let dataArr = [];
        for (var i = 0; i < 80; i++) {
          if (i % 2 === 0) {
            dataArr.push({
              name: (i + 1).toString(),
              value: 1,
              itemStyle: {
                normal: {
                  color: "#0CD3DB",
                  borderWidth: 1,
                  borderColor: "#0CD3DB",
                },
              },
            });
          } else {
            dataArr.push({
              name: (i + 1).toString(),
              value: 2,
              itemStyle: {
                normal: {
                  color: "rgba(0,0,0,0)",
                  borderWidth: 0,
                  borderColor: "rgba(0,0,0,0)",
                },
              },
            });
          }
        }
        return dataArr;
      }
      //myChart.setOption(option);
      //获取圆上面某点的坐标(x0,y0表示坐标，r半径，angle角度)
      function getCirlPoint(x0, y0, r, angle) {
        let x1 = x0 + r * Math.cos((angle * Math.PI) / 180);
        let y1 = y0 + r * Math.sin((angle * Math.PI) / 180);
        return {
          x: x1,
          y: y1,
        };
      }

      function draw() {
        angle = angle + 3;
        that.inOutPieChart.setOption(option, true);
        //window.requestAnimationFrame(draw);
      }

      setInterval(function () {
        //用setInterval做动画感觉有问题
        draw();
      }, 100);
    },
    //  初入详细数据分析 饼状图
    intChart() {
      this.inOutPieChart = echarts.init(this.$refs["angle"]);
      this.inOutLineChart = echarts.init(this.$refs["lineOne"]);
    },
    //  初入详细数据分析 折线
    setInOutLineOptions() {
      let that = this;
      let angle = 0; //角度，用来做简单的动画效果的
      let option = {
        grid: {
          top: 40,
          right: 20,
          left: 0,
          bottom: 0,
          containLabel: true,
        },
        /*tooltip: {
          axisPointer: {
            crossStyle: {
              color: "#999",
            },
          },
        },*/
        tooltip: {
          trigger: "axis",
          axisPointer: {
            label: {
              show: true,
              //backgroundColor: '#fff',
              color: "#556677",
              borderColor: "rgba(0,0,0,0)",
              shadowColor: "rgba(0,0,0,0)",
              shadowOffsetY: 0,
            },
            lineStyle: {
              width: 0,
            },
          },
          //backgroundColor: '#fff',
          textStyle: {
            color: "#fff",
          },
          padding: [10, 10],
          extraCssText: "box-shadow: 1px 0 2px 0 rgba(163,163,163,0.5)",
        },
        xAxis: {
          boundaryGap: false,
          type: "category",
          axisTick: {
            show: false,
          },
          data: [
            "1月",
            "2月",
            "3月",
            "4月",
            "5月",
            "6月",
            "7月",
            "8月",
            "9月",
            "10月",
            "11月",
            "12月",
          ],
          axisLine: {
            lineStyle: {
              color: "#337B89",
            },
          },
          axisLabel: {
            color: "#337B89",
            fontSize: 10,
            margin: 15,
          },
        },
        yAxis: {
          name: "单位（次）",
          nameTextStyle: {
            color: "#337B89",
            fontSize: 10,
            align: "left",
          },
          nameGap: 20,
          type: "value",
          splitLine: {
            show: false,
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: "#337B89",
            },
          },
          axisTick: {
            show: false,
          },
          axisLabel: {
            color: "#337B89",
            fontSize: 10,
            margin: 15,
          },
        },
        series: [
          {
            name: "人行",
            data: that.thisYearUser,
            type: "line",
            lineStyle: {
              color: "#02EDE5",
            },
            symbol: "circle",
            smooth: true,
            color: "#02EDE5",
            symbolSize: 3,
            itemStyle: {
              borderWidth: 3,
              borderColor: "rgba(36,132,144, .2)",
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: "rgba(36,132,144, 0.5)",
                },
                {
                  offset: 1,
                  color: "rgba(36,132,144, 0.01)",
                },
              ]),
            },
          },
          {
            name: "车行",
            data: that.thisYearVehicle,
            type: "line",
            lineStyle: {
              color: "#B5863D",
            },
            symbol: "circle",
            smooth: true,
            color: "rgba(181, 134, 61)",
            symbolSize: 3,
            itemStyle: {
              borderWidth: 3,
              borderColor: "rgba(181, 134, 61, .5)",
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: "rgba(181, 134, 61, 0.5)",
                },
                {
                  offset: 1,
                  color: "rgba(181, 134, 61,  0.01)",
                },
              ]),
            },
          },
        ],
      };
      that.inOutLineChart.setOption(option);
      this.dynamic(that.inOutLineChart, 24);
    },
    // 小区环境 监测 折线
    brokenLineTwo() {
      let that = this;
      const myChart = echarts.init(this.$refs["lineTwo"]);
      let option = {
        tooltip: {
          // backgroundColor:'#ccc',
          type: "cross",
          trigger: "axis",
          formatter: function (params) {
            let res = "";
            for (let i = 0; i < params.length - 1; i++) {
              let series = params[i];
              res +=
                series.marker + series.seriesName + ":" + series.data + "<br/>";
            }
            return res;
          },
        },
        legend: {
          top: 5,
          itemWidth: 1,
          itemHeight: 8,
          itemGap: 10,
          left: "15%",
          textStyle: {
            color: " #3A90A2",
            fontSize: 10,
          },
          data: ["车辆进入", "车辆驶出"],
        },
        grid: {
          top: "14%",
          left: "3%",
          right: "4%",
          bottom: "0.1%",
          containLabel: true,
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          axisLine: {
            lineStyle: {
              color: "#3F797F",
            },
          },
          axisTick: {
            show: false,
          },
          axisLabel: {
            show: true,
            textStyle: {
              color: "#3F797F",
              fontSize: 8,
            },
          },
          data: [
            "00:00",
            "02:00",
            "04:00",
            "06:00",
            "08:00",
            "10:00",
            "12:00",
            "14:00",
            "16:00",
            "18:00",
            "20:00",
            "22:00",
            "24:00",
          ],
        },
        yAxis: {
          type: "value",
          name: "单位：（辆）",
          min: 0,
          minInterval: 1,
          nameTextStyle: {
            color: "#3F797F",
            fontSize: 8,
            padding: [15, 0, 0, -16],
          },
          axisLine: {
            show: false,
            lineStyle: {
              color: "#02101F",
            },
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: "#02101F",
            },
          },
          axisTick: {
            show: false,
          },
          axisLabel: {
            formatter: "{value}",
            color: "#3F797F",
            fontSize: 8,
          },
          boundaryGap: ["20%", "20%"],
        },
        series: [
          {
            name: "车辆进入",
            type: "line",
            symbol: "none",
            itemStyle: {
              normal: {
                color: "#01D9D1", //图例的颜色
                lineStyle: {
                  color: "#01D9D1", //线的颜色
                },
              },
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                  0,
                  0,
                  0,
                  1,
                  [
                    {
                      offset: 0,
                      color: "rgba(1,217,209,0.3)",
                    },
                    {
                      offset: 1,
                      color: "rgba(1,217,209,0)",
                    },
                  ],
                  false
                ),
                shadowColor: "rgba(1,217,209, 0.9)",
                shadowBlur: 20,
              },
            },
            markLine: {
              symbol: "none",
              label: {
                show: true,
              },
              data: [
                {
                  xAxis: "09:00",
                  name: "今日",
                },
              ],
            },
            data: that.listAndOutIn,
          },
          {
            name: "车辆驶出",
            type: "line",
            symbol: "none",
            itemStyle: {
              normal: {
                color: "#C89D59", //图例的颜色
                lineStyle: {
                  color: "#C89D59", //线的颜色
                },
              },
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                  0,
                  0,
                  0,
                  1,
                  [
                    {
                      offset: 0,
                      color: "rgba(200,157,89,0.3)",
                    },
                    {
                      offset: 1,
                      color: "rgba(200,157,89,0)",
                    },
                  ],
                  false
                ),
                shadowColor: "rgba(200,157,89,0.9)",
                shadowBlur: 20,
              },
            },
            data: that.listAndOutOut,
          },
          // {
          //   name: "对比",
          //   type: "line",
          //   symbol: "none",
          //   itemStyle: {
          //     normal: {
          //       color: "#ccc", //图例的颜色
          //       lineStyle: {
          //         type: "dotted", //'dotted'虚线 'solid'实线
          //         width: 0,
          //         color: "#77f0c4", //线的颜色
          //       },
          //     },
          //   },
          //   data: that.listAndOutOut,
          // },
        ],
      };
      myChart.setOption(option);
      this.dynamic(myChart, 6);
    },
    // 社区信息
    cakeTwo() {
      const myChart = echarts.init(this.$refs["cakeTwo"]);
      let trafficWay = [
        {
          name: "保洁人员",
          value: this.staffSecurity,
        },
        {
          name: "安保人员",
          value: this.staffProperty,
        },
        {
          name: "维修人员",
          value: this.staffRepair,
        },
      ];
      let sum = 18;
      let color = [
        "#1fab99",
        "#17a3df",
        "#f7746e",
        "#F8456B",
        "#00FFFF",
        "#4AEAB0",
      ];

      let option = {
        // backgroundColor: '#091C2F',
        color: color,
        // 标题
        title: {
          // text: '{a|' +'管理人员'+ sum + '}\n{c|' + '安保人员' +sum +'}\n{c|' + '维修人员' + sum+ '}',
          top: "center",
          left: "center",
          textStyle: {
            rich: {
              a: {
                fontSize: 8,
                color: "#FFFFFF",
              },
              c: {
                fontSize: 8,
                color: "#EEEEEE",
                padding: [5, 5],
              },
            },
          },
        },
        series: [
          {
            //内圆
            type: "pie",
            radius: "50%",
            center: ["50%", "50%"],
            z: 0,
            itemStyle: {
              normal: {
                color: new echarts.graphic.RadialGradient(
                  0.5,
                  0.5,
                  1,
                  [
                    {
                      offset: 0,
                      color: "rgba(17,24,43,0.1)",
                    },
                    {
                      offset: 0.5,
                      color: "rgba(28,42,91,.6)",
                    },
                    {
                      offset: 1,
                      color: "#141C33",
                      // color:'rgba(17,24,43,0)'
                    },
                  ],
                  false
                ),
                label: {
                  show: false,
                },
                labelLine: {
                  show: false,
                },
              },
            },
            label: {
              show: false,
            },
            tooltip: {
              show: false,
            },
            data: [100],
          },
          {
            type: "pie",
            center: ["50%", "50%"],
            radius: ["65%", "50%"],
            selectedMode: "single",
            selectedOffset: 10,
            clockwise: true,
            avoidLabelOverlap: true,
            hoverOffset: 5,
            itemStyle: {
              normal: {
                color: function (params) {
                  return color[params.dataIndex];
                },
              },
            },
            label: {
              show: true,
              position: "outside",
              padding: [12, 0, 0.0],
              fontSize: 8,
              formatter: "{b|{b}}\n{a|{c}人}\n{radius|●}",
              rich: {
                radius: {
                  padding: [0, 0, -30, 0],
                },
                b: {
                  padding: [-20, 20, 25, 20],
                  fontSize: 8,
                },
                a: {
                  padding: [-20, 20, -45, 20],
                  fontSize: 8,
                },
              },
            },
            labelLine: {
              show: true,
              normal: {
                length: 1,
                length2: 1,
                show: true,
                lineStyle: {
                  width: 1,
                },
              },
            },
            data: trafficWay,
          },
          {
            //刻度
            type: "gauge",
            radius: "42%",
            center: ["50%", "50%"],
            min: 0,
            max: 100,
            splitNumber: 10, //刻度数量
            startAngle: 320,
            endAngle: -40,
            axisLine: {
              // 坐标轴线
              show: false,
              lineStyle: {
                // 属性lineStyle控制线条样式
                width: 0,
                shadowColor: "#67FFFC",
                shadowBlur: 2,
                color: [
                  [0.23, "#2EEAFF"],
                  [0.76, " #148AFF"],
                  [1, "#2EEAFF"],
                ],
              },
            },
            axisTick: {
              // 坐标轴小标记
              length: 20, // 属性length控制线长
              lineStyle: {
                // 属性lineStyle控制线条样式
                color: "auto",
                width: 2,
              },
            },
            axisLabel: {
              show: false,
            },
            splitLine: {
              // 分隔线
              length: -5,
              lineStyle: {
                color: "auto",
                width: 2,
              },
            },
            // 指针
            pointer: {
              show: false,
            },
            detail: {
              show: false,
            },
          },
        ],
      };
      function _dashed() {
        let dataArr = [];
        for (var i = 0; i < 100; i++) {
          if (i % 2 === 0) {
            dataArr.push({
              name: (i + 1).toString(),
              value: 10,
              itemStyle: {
                normal: {
                  color: "rgb(0,118,239,0.5)",
                },
              },
            });
          } else {
            dataArr.push({
              name: (i + 1).toString(),
              value: 50,
              itemStyle: {
                normal: {
                  color: "rgb(0,0,0,0)",
                  borderWidth: 1,
                  borderColor: "rgb(0,118,239,1)",
                },
              },
            });
          }
        }
        return dataArr;
      }

      function doing() {
        let option = myChart.getOption();
        option.series[1].startAngle = option.series[1].startAngle - 1;
        myChart.setOption(option);
      }

      function startTimer() {
        let timer;
        timer = setInterval(doing, 100);
        return timer;
      }
      setTimeout(startTimer, 0);
      myChart.setOption(option);
    },
  },
};
</script>

<style scoped>
.anim {
  transition: all 0.5s;
}
.sut {
  box-shadow: 0 0 0.5rem #1890ff inset;
  color: #1890ff;
}
.BGO1 {
  width: 140rem;
  height: 79rem;
  /* background: url("~@/assets/large/bgone.png"); */
  background-size: 100% 100%;
}
::v-deep .mobile25 ul li {
  width: 0 !important;
}
.BG_MAX {
  font-size: 1rem;
  width: 140rem;
  height: 71rem;
  background: url("~@/assets/large/bgtwo.png");
  background-size: 100% 100%;
  box-sizing: border-box;
}
::v-deep .mobile25 {
  width: 300px;
  font-size: 18px !important;
}
.white {
  color: #cefffd;
  font-size: 1rem;
  margin-top: 0.6rem;
}
.time {
  margin-left: 0vw;
}
.residential {
  margin-left: 2.6vw;
  margin-top: 0.7rem;
}
.item_01 {
  border: 0.1vh solid #0dc0bd;
  text-align: center;
  border-radius: 0.3rem;
  font-size: 0.7rem;
  padding: 0.25rem;
  margin: -0.35rem 0 0 2rem;
  box-shadow: 0 0 0.5rem #0dc0bd inset;
}
@font-face {
  font-family: electronicFont;
  src: url(~@/assets/font/DS-DIGIT.TTF);
}
.t0 {
  font-family: electronicFont;
}
.Rg {
  margin-left: 0.7rem;
}
.Cr {
  color: rgba(17, 101, 178, 0.8);
}
::v-deep .el-slider__runway {
  height: 0.6rem;
  margin: 0.4rem;
  border-radius: 0.3rem;
  width: 85%;
  background-color: #346ca5;
}
::v-deep .el-slider__button {
  width: 0;
  height: 0;
  border: 0;
}
::v-deep .el-slider__bar {
  height: 0.6rem;
  border-radius: 0.3rem;
  background: #5ddde8;
}
::v-deep .el-progress-bar__innerText {
  font-size: 0.5rem;
  margin: 0 0 1rem 0;
  color: #02ede5;
}
::v-deep .el-progress-bar__outer {
  background-color: rgba(66, 80, 65, 0.9);
}
.number_q {
  margin-top: 3.8rem;
  font-family: electronicFont;
  height: 7.7rem;
  text-align: center;
  letter-spacing: 2rem;
  margin-left: -2.2rem;
  color: #47ffff;
}
.eys01 {
  margin: 6rem 0 0 2rem;
  background-color: #051d33;
  width: 5rem;
}
.eys02 {
  margin: 4rem 0 0 0.1rem;
}
.index_01 {
  background: #00c5bd;
}
.eys03 {
  margin: 4rem 0 0 0.8rem;
}
/deep/ .el-input__inner {
  background-color: transparent;
  color: #fff;
  border: 0px;
  border-radius: 0px;
  text-align: center;
}

@media screen and (max-width: 1680px) {
  .number_q {
    margin-top: 3.1rem;
  }
}
</style>
<style lang="scss">
.environment {
  position: relative;
  height: 100%;
  .number {
    display: flex;
    position: absolute;
    top: 1.2rem;
    right: 0.4rem;
    width: 15rem;
    height: 1.8rem;
    // background-color: yellow;
  }

  .typeItemBox {
    // margin-top: 5rem;
    margin-left: 8.8rem;
    overflow: hidden;
    // margin-right: 1.8rem;
    overflow-x: auto;
    position: absolute;
    top: 0;
    margin-top: 3.8rem;
    right: 0;
    width: 70%;
    height: 1.6rem;
    line-height: 1.6rem;
    z-index: 1;

    .list {
      display: flex;
      justify-content: flex-start;
      align-items: center;
      width: max-content;

      color: rgba(255, 255, 255, 0.6);

      .item {
        // margin-left: 20px;
        cursor: pointer;
        font-size: 0.8rem;
        padding: 0 10px;
      }

      .active {
        background: url(~@/assets/large/Group401806@2x.png);
        background-size: 100% 100%;
        color: rgba(255, 255, 255, 1);
      }
    }

    /* 修改垂直滚动条 */
    &::-webkit-scrollbar {
      width: 6px;
      height: 3px;
      /* 修改宽度 */
    }

    /* 修改滚动条轨道背景色 */
    &::-webkit-scrollbar-track {
      background-color: #f1f1f1;
    }

    /* 修改滚动条滑块颜色 */
    &::-webkit-scrollbar-thumb {
      background-color: #174b6a;
    }

    /* 修改滚动条滑块悬停时的颜色 */
    &::-webkit-scrollbar-thumb:hover {
      background-color: #174b6a;
    }
  }

  .el-input--medium .el-input__inner,
  .el-input--medium .el-input__icon {
    height: 24px;
    line-height: 24px;
  }

  .el-input__inner {
    background: none;
    border: none;
    color: #5be9ff;
    text-align: right;
  }

  .el-select {
    z-index: 11;
    width: auto;
  }

  .el-select .el-input .el-select__caret {
    color: #5be9ff;
  }

  // 设置下拉框的背景颜色及边框属性；
  .el-select-dropdown {
    width: auto !important;
    min-width: 50px !important;
    border: 1px solid #1f7b9c;
    box-shadow: inset 2px 2px 5px #1f7b9c;
    // 若不将下拉框的背景颜色设置为：transparent，那么做不出来半透明的效果；
    // 因为其最终的显示为：下拉框有一个背景颜色且下拉框的字体有一个背景颜色，重叠后的效果展示；
    background-color: transparent;
    background: #0c2c42;
    // border: 1px solid blue;
  }

  // 设置下拉框的字体属性及背景颜色；
  .el-select-dropdown__item {
    color: #fff;
    background-color: none;
  }

  // 设置下拉框列表的 padding 值为：0；(即：样式调整)
  .el-select-dropdown__list {
    padding: 0 !important;
  }

  // 设置输入框与下拉框的距离为：0; (即：样式调整)
  .el-select-dropdown.el-popper {
    margin-top: 0px;
    left: auto !important;
    right: 0 !important;
  }

  // 将下拉框上的小箭头取消；(看着像一个箭头，其实是两个重叠在一起的箭头)
  .el-popper .popper__arrow,
  .el-popper .popper__arrow::after {
    display: none;
  }

  // 设置鼠标悬停在下拉框列表的悬停色；
  .el-select-dropdown__item.hover,
  .el-select-dropdown__item:hover {
    background-color: rgba($color: #348aa0, $alpha: 0.2);
    color: #59e3f9;
  }
}
</style>
