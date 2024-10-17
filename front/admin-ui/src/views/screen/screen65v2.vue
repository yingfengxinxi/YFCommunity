<template>
  <div
    class="largeScreen"
    :style="
      params.projects2 == '城建·彩金湖畔'
        ? coverImgUrl
        : params.projects2 == '城建·世纪阳江'
        ? coverImgUrl2
        : coverImgUrl3
    "
  >
    <div class="backgroundMap">
      <!-- 大屏展示顶部 -->
      <div class="largeScreen_top">
        <!-- 地区 -->
        <div class="region">
          <!-- <div class="region_left">
            <img src="../../assets/largeScreen/Subtract(1).png" alt="" />
            <div class="city" style="">{{ city }}</div>
          </div>
          <div class="region_right" style="color: #1890ff">
            {{ params.projects2 }}
          </div> -->
        </div>
        <!-- 宣城城建智慧城市管理平台 -->
        <!-- <div class="platform">宣城城建智慧城市管理平台</div> -->
        <!-- 时间 -->
        <div class="time">
          <!-- <div class="time_left">
            <div class="temperature">17°</div>
            <div class="weather">
              <img src="../../assets/largeScreen/taiyang.png" alt="" />

            </div>
          </div> -->
          <!-- <div style="display: flex;">
            <div
              class="font_family"
              style="font-size: 2.8rem; color: #5be9ff;font-family: electronicFont;line-height: 3.2rem;"
            >
              17°
            </div>
            <div
              style="
                      margin-left: 0.5rem;
                      width: 4rem;
                    "
            >
              <div style="width: 3.1rem;">
                <img
                  src="../../assets/largeScreen/taiyang.png"
                  alt=""
                  style="width: 1.2rem; height: 1.2rem"
                />
                <span
                  style="
                          margin-left: 0.463934rem;
                          font-size: 1.2rem;
                          color: #5be9ff;
                        "
                  >晴</span
                >
              </div>
              <div style="color: #5be9ff;">5°/18°</div>
            </div>
          </div> -->
          <div style="height: 3rem; line-height: 8.6rem">
            <!-- <iframe width="150" scrolling="no" height="50" frameborder="0" allowtransparency="true" src="https://i.tianqi.com?c=code&id=154&color=%2300B0F0&icon=1&py=xuancheng&site=14"></iframe> -->
            <iframe
              width="160"
              scrolling="no"
              height="50"
              frameborder="0"
              allowtransparency="true"
              src="https://i.tianqi.com?c=code&id=154&color=%2300B0F0&icon=1&py=xuancheng&site=14"
            ></iframe>
          </div>
          <div class="specific">{{ nowTime3 }}</div>
          <div class="week">{{ nowTime2 }}</div>
          <div class="hour" style="width: 14rem; letter-spacing: 0.6rem">
            {{ nowTime }}
          </div>
        </div>
      </div>
      <!-- <div
        style="display: flex; width: 420rem; height: 78.75rem; overflow: hidden"
      > -->
      <div
        style="display: flex; width: 420rem; height: 78.75rem; overflow: hidden"
      >
        <!-- 左侧大屏 -->
        <div class="largeScreen_left">
          <!-- 左上大屏 -->
          <div class="upperLeft">
            <!-- 物业服务人员 -->
            <div class="property" style="">
              <!-- 物业服务人员第一行 -->
              <div class="total" style="height: 8rem">
                <!-- 总人数 -->
                <div
                  class="number"
                  style="display: flex; width: 14rem; height: 2.786885rem"
                >
                  <img src="../../assets/largeScreen/zongrenshu.png" alt="" />
                  <div class="number_text" style="margin-left: 0.983607rem">
                    <div style="color: #ffffff; font-size: 0.883607rem">
                      {{ servicePersonnel.deptName }}
                    </div>
                    <div style="color: #47ffff">
                      <span
                        style="font-family: Impact; font-size: 0.883607rem"
                        >{{ servicePersonnel.sum }}</span
                      >
                      <span style="font-size: 0.883607rem">人</span>
                    </div>
                  </div>
                </div>

                <!-- 物业场馆人员 -->
                <div
                  class="number"
                  style="display: flex; width: 10rem; height: 2.786885rem"
                >
                  <img
                    src="../../assets/largeScreen/wuyeguanlirenyuan.png"
                    alt=""
                  />
                  <div class="number_text" style="margin-left: 0.983607rem">
                    <div style="color: #ffffff; font-size: 0.883607rem">
                      物业保洁人员
                    </div>
                    <div style="color: #47ffff">
                      <span
                        style="font-family: Impact; font-size: 0.883607rem"
                        >{{ servicePersonnel.wyglStaffNumber }}</span
                      >
                      <span style="font-size: 0.883607rem">人</span>
                    </div>
                  </div>
                </div>
                <!-- 物业维修人员 -->
                <div
                  class="number"
                  style="display: flex; width: 14rem; height: 2.786885rem"
                >
                  <img src="../../assets/largeScreen/Group1555.png" alt="" />
                  <div class="number_text" style="margin-left: 0.983607rem">
                    <div style="color: #ffffff; font-size: 0.883607rem">
                      物业维修人员
                    </div>
                    <div style="color: #47ffff">
                      <span
                        style="font-family: Impact; font-size: 0.883607rem"
                        >{{ servicePersonnel.wxStaffNumber }}</span
                      >
                      <span style="font-size: 0.883607rem">人</span>
                    </div>
                  </div>
                </div>
                <!-- 物业安保人员 -->
                <div
                  class="number"
                  style="display: flex; width: 10rem; height: 2.786885rem"
                >
                  <img src="../../assets/largeScreen/Group1556.png" alt="" />
                  <div class="number_text" style="margin-left: 0.983607rem">
                    <div style="color: #ffffff; font-size: 0.883607rem">
                      物业安保人员
                    </div>
                    <div style="color: #47ffff">
                      <span
                        style="font-family: Impact; font-size: 0.883607rem"
                        >{{ servicePersonnel.afStaffNumber }}</span
                      >
                      <span style="font-size: 0.883607rem">人</span>
                    </div>
                  </div>
                </div>
              </div>
              <!-- 物业服务人员第三行 -->
              <div class="proportion">
                <!-- 物业服务人员进度条 -->
                <div ref="option5" style="width: 100%; height: 100%"></div>
              </div>
            </div>
            <!-- 小区房间房态数量统计 -->
            <div class="room">
              <!-- 小区横向柱状图 -->
              <div
                ref="option4"
                style="width: 100%; height: 100%; margin-top: 1.16rem"
              ></div>
            </div>
            <!-- 业主年龄分布 -->
            <div class="age">
              <div
                class="age_top"
                style="width: 17.868852rem; height: 0.655738rem"
              >
                <div class="age_nine" style="">
                  <div
                    style="
                      width: 0.655738rem;
                      height: 100%;
                      background-color: #0140c7;
                    "
                  ></div>
                  <div
                    style="
                      margin-left: 0.16rem;
                      color: #00f6ec;
                      font-size: 0.655738rem;
                      line-height: 0.655738rem;
                    "
                  >
                    0~10岁
                  </div>
                </div>
                <div class="age_eighteen" style="">
                  <div
                    style="
                      width: 0.655738rem;
                      height: 100%;
                      background-color: #0172ca;
                    "
                  ></div>
                  <div
                    style="
                      margin-left: 0.16rem;
                      color: #00f6ec;
                      font-size: 0.655738rem;
                      line-height: 0.655738rem;
                    "
                  >
                    11~20岁
                  </div>
                </div>
                <div class="age_forty" style="">
                  <div
                    style="
                      width: 0.655738rem;
                      height: 100%;
                      background-color: #138af5;
                    "
                  ></div>
                  <div
                    style="
                      margin-left: 0.16rem;
                      color: #00f6ec;
                      font-size: 0.655738rem;
                      line-height: 0.655738rem;
                    "
                  >
                    21~40岁
                  </div>
                </div>
                <div class="age_fortyone" style="">
                  <div
                    style="
                      width: 0.655738rem;
                      height: 100%;
                      background-color: #2dd7fe;
                    "
                  ></div>
                  <div
                    style="
                      margin-left: 0.16rem;
                      color: #00f6ec;
                      font-size: 0.655738rem;
                      line-height: 0.655738rem;
                    "
                  >
                    41岁以上
                  </div>
                </div>
              </div>
              <div style="display: flex">
                <!-- 3D饼状图 -->
                <div>
                  <div
                    ref="option2"
                    style="width: 10.2rem; height: 10.2rem; margin-left: 2rem"
                  ></div>
                </div>
                <!-- 业主横向柱状图 -->
                <div>
                  <div
                    ref="option3"
                    style="width: 18rem; height: 12rem; margin-left: 2rem"
                  ></div>
                </div>
              </div>
            </div>
            <!-- 社区人员结构 -->
            <div class="community" style="display: flex">
              <!-- 饼图引导线 -->
              <div
                ref="option"
                style="width: 19.6rem; height: 16.2rem; margin-left: -0.2rem"
              ></div>
              <div class="community_right" style="width: 11.8rem">
                <!-- 业户总数 -->
                <div
                  class="households"
                  style="
                    width: 13.196721rem;
                    height: 2.95082rem;
                    line-height: 2.95082rem;
                  "
                >
                  <span style="color: #fff; margin-left: 0.983607rem"
                    >业户总数：</span
                  >
                  <span style="color: #47ffff; font-weight: bold">{{
                    checkIn3.ownerTotal
                  }}</span>
                  <span style="color: #47ffff; margin-left: 0.163934rem"
                    >户</span
                  >
                </div>
                <!-- 租户总数 -->
                <div
                  class="tenants"
                  style="
                    width: 13.196721rem;
                    height: 2.95082rem;
                    line-height: 2.95082rem;
                  "
                >
                  <span style="color: #fff; margin-left: 0.983607rem"
                    >租户总数：</span
                  >
                  <span style="color: #47ffff; font-weight: bold">{{
                    checkIn3.tenantTotal
                  }}</span>
                  <span style="color: #47ffff; margin-left: 0.163934rem"
                    >户</span
                  >
                </div>
              </div>
            </div>
          </div>
          <!-- 左中大屏 -->
          <div class="left_center" style="">
            <!-- 社区能源统计 -->
            <div class="energy" style="width: 68.8rem; height: 100%">
              <!-- 小区情况 -->
              <div class="company_situation">
                <div
                  class="company_situation_top"
                  style="color: #00f6ec; font-size: 0.883607rem"
                >
                  小区情况
                </div>
                <div class="bg">
                  <img src="../../assets/largeScreen/companyBg.png" alt="" />
                </div>
                <div class="grid">
                  <div class="item">
                    <div class="image">
                      <img src="../../assets/largeScreen/loudong.png" alt="" />
                    </div>
                    <div class="text">
                      <div class="title">楼栋数量</div>
                      <div class="number">
                        <span> {{ checkIn4.buildingNum || "0" }} </span>
                        栋
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="image">
                      <img
                        src="../../assets/largeScreen/xiaoquhushu.png"
                        alt=""
                      />
                    </div>
                    <div class="text">
                      <div class="title">小区户数</div>
                      <div class="number">
                        <span> {{ checkIn4.ownerNum || "0" }} </span>
                        户
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="image">
                      <img
                        src="../../assets/largeScreen/xiaoqurenshu.png"
                        alt=""
                      />
                    </div>
                    <div class="text">
                      <div class="title">小区人数</div>
                      <div class="number">
                        <span> {{ checkIn4.peopleNum || "0" }} </span>
                        人
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="image">
                      <img
                        src="../../assets/largeScreen/tingchewei.png"
                        alt=""
                      />
                    </div>
                    <div class="text">
                      <div class="title">停车位数量</div>
                      <div class="number">
                        <span> {{ checkIn4.carportNum || "0" }} </span>
                        个
                      </div>
                    </div>
                  </div>
                  <div class="item">
                    <div class="image">
                      <img
                        src="../../assets/largeScreen/xiaoqucheliang.png"
                        alt=""
                      />
                    </div>
                    <div class="text">
                      <div class="title">小区车辆数量</div>
                      <div class="number">
                        <span> {{ checkIn4.vehicleNum || "0" }} </span>
                        辆
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="quarter">
                <div style="display: flex">
                  <div
                    class="quarter_top"
                    style="color: #00f6ec; font-size: 0.883607rem"
                  >
                    居住舒适质量
                  </div>
                </div>
                <div style="height: 78%">
                  <div class="quarter_Num">
                    <div class="item">
                      <div class="image">
                        <img
                          src="../../assets/largeScreen/zhandimianji.png"
                          alt=""
                        />
                      </div>
                      <div class="text">
                        <div class="title">占地面积</div>
                        <div class="number">
                          <span> {{ checkIn5.coveredArea }} </span>
                          平方
                        </div>
                      </div>
                    </div>
                    <div class="item">
                      <div class="image">
                        <img
                          src="../../assets/largeScreen/jianzhumianji.png"
                          alt=""
                        />
                      </div>
                      <div class="text">
                        <div class="title">建筑面积</div>
                        <div class="number">
                          <span> {{ checkIn5.landSpace }} </span>
                          平方
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="quarter_echarts">
                    <div
                      class="cl-top-content"
                      id="quarterEcharts"
                      ref="option9"
                      style="width: 100%; height: 12rem"
                    ></div>
                  </div>
                </div>
              </div>
            </div>
            <div class="center_right" style="">
              <div class="right_top" style="">
                <!-- 访客统计 -->
                <div class="visitor" style="">
                  <div
                    style="
                      position: relative;
                      width: 100%;
                      height: 50%;
                      margin-top: 1.65rem;
                    "
                  >
                    <div class="number" style="">
                      <!-- 访客统计下拉框 -->
                      <!-- <span style="color:#5BE9FF;margin-left: 2.4rem;">{{one}}年</span> -->
                      <select
                        v-model="month"
                        style="
                          margin-top: -0.4rem;
                          margin-left: 10.3rem;
                          outline: none;
                          border: 0;
                          background-color: unset;
                          color: #5be9ff;
                          z-index: 999;
                        "
                        @change="getQueryDaily()"
                      >
                        <!-- <option value="">1月</option> -->
                        <option
                          v-for="x in month2"
                          style="background-color: unset; color: #5be9ff"
                          :value="x.name"
                        >
                          {{ x.name }}月
                        </option>
                      </select>
                    </div>
                    <!-- 访客统计折线图 -->
                    <div ref="option6" style="width: 100%; height: 100%"></div>
                  </div>
                  <div
                    style="
                      position: relative;
                      width: 100%;
                      height: 52%;
                      margin-left: -0.5rem;
                      margin-top: -0.6rem;
                    "
                  >
                    <div class="number" style="">
                      <!-- 访客人次 -->
                      <div
                        class="visitors"
                        style="display: flex; width: 5.4rem; height: 1.2rem"
                      >
                        <div
                          class="colour"
                          style="
                            width: 1.2rem;
                            height: 100%;
                            background-color: #5be9ff;
                          "
                        ></div>
                        <div
                          class="words"
                          style="
                            width: 3.9rem;
                            font-size: 0.85rem;
                            margin-left: 0.6rem;
                            color: #fff;
                          "
                        >
                          访客人次
                        </div>
                      </div>
                      <!-- 访客统计下拉框 -->
                      <!-- <span style="color:#5BE9FF;margin-left: 2.4rem;">{{one}}年</span> -->
                      <select
                        v-model="year"
                        style="
                          margin-top: -0.4rem;
                          margin-left: 4.79rem;
                          outline: none;
                          border: 0;
                          background-color: unset;
                          color: #5be9ff;
                          z-index: 999;
                        "
                        @change="getQueryYear()"
                      >
                        <!-- <option value="">2021年</option> -->
                        <option
                          v-for="x in year2"
                          style="background-color: unset; color: #5be9ff"
                          :value="x.name"
                        >
                          {{ x.name }}年
                        </option>
                      </select>
                    </div>
                    <!-- 立体渐变柱状图 -->
                    <div ref="option7" style="width: 108%; height: 100%"></div>
                  </div>
                </div>
                <!-- 中间左侧 -->
                <div class="center_left">
                  <!-- 小区人流量统计 -->
                  <div class="trafficStatistics">
                    <div
                      ref="flow_single"
                      style="
                        width: 100%;
                        height: 86%;
                        margin: 2.3rem 0px 0px 0.2rem;
                      "
                    ></div>
                  </div>
                  <!-- 进出车流量统计 -->
                  <div class="trafficFlow">
                    <div
                      ref="flow_double"
                      style="width: 100%; height: 100%; margin: 0.3rem 0px 0px"
                    ></div>
                  </div>
                </div>
              </div>
              <!-- 小区人行/车行进出流量走势图 -->
              <div
                class="right_bottom"
                style="position: relative; padding-top: 1rem"
              >
                <div class="number" style="">
                  <!-- 社区人行下拉框 -->
                  <!-- <span style="color:#5BE9FF;margin-left: 2.4rem;">{{one}}年</span> -->
                  <select
                    v-model="month3"
                    style="
                      margin-top: -0.2rem;
                      margin-left: 10.3rem;
                      outline: none;
                      border: 0;
                      background-color: unset;
                      color: #5be9ff;
                      z-index: 999;
                    "
                    @change="inOutHuman()"
                  >
                    <!-- <option value="">1月</option> -->
                    <option
                      v-for="x in month4"
                      style="background-color: unset; color: #5be9ff"
                      :value="x.name"
                    >
                      {{ x.name }}月
                    </option>
                  </select>
                </div>
                <!-- 社区人行折线图 -->
                <div ref="option8" style="width: 100%; height: 98%"></div>
              </div>
            </div>
          </div>
        </div>
        <!-- 中间大屏 -->
        <!-- <div class="largeScreen_center"></div> -->
        <div class="largeScreen_center" :style="styleVar">
          <!-- 小区实时监控 -->
          <div
            class="monitoring"
            style=""
            v-show="this.communityMonitoring == true"
          >
            <!-- <div class="monitoring_top" ref="monitoringTop">
              <div class="" style="text-align: center; color: #53daf1">
                <span v-show="!playerShow">{{ playerText }}</span>
                <CameraPreview
                  v-if="playerShow"
                  ref="camera"
                  :split="splitNum"
                  :playURL="playURL"
                  :width="cameraWidth"
                  :height="cameraHeight"
                />
              </div>
            </div> -->
            <!-- <div
              class="monitoring_"
              style="position: relative; overflow: hidden"
            >
              <div
                style="
                  display: flex;
                  justify-content: space-around;
                  height: 2rem;
                  margin-top: 2.5rem;
                  color: #53daf1;
                "
              >
                <div>序号</div>
                <div>摄像头名称</div>
              </div>
              <div
                style="
                  position: absolute;
                  left: 0;
                  top: 0;
                  right: -17px;
                  bottom: 0;
                  overflow-x: hidden;
                  overflow-y: scroll;
                  margin-top: 5.6rem;
                  height: 22rem;
                "
              >
                <div
                  style=""
                  v-for="(i, j) in monitors"
                  :key="j"
                  :class="{ actived: j == isactive }"
                >
                  <div
                    style="
                      display: flex;
                      text-align: center;
                      height: 3rem;
                      margin-top: 0.6rem;
                      line-height: 3rem;
                      color: #53daf1;
                      cursor: pointer;
                    "
                    @click="clickMonitoring(i, j)"
                  >
                    <div style="width: 45%">{{ j + 1 }}</div>
                    <div style="width: 55%">{{ i.cameraName }}</div>
                  </div>
                </div>
              </div>
            </div> -->
          </div>
          <!-- 中间中间 -->
          <div class="center_center">
            <!-- 选择省市区 -->

            <div class="province">
              <div
                class="choice"
                style="color: #5be9ff; margin: -2.5rem 0px 0px 0.8rem"
              >
                选择区域：
              </div>
              <treeselect
                v-model="areaId"
                :options="areaList"
                :show-count="true"
                :default-expand-level="Infinity"
                :disable-branch-nodes="true"
                @select="selectArea"
                placeholder="请选择区域"
              />
            </div>

            <!-- 选择项目 -->
            <div class="project">
              <div
                class="choice"
                style="color: #5be9ff; margin: -1.8rem 0px 0px 0.8rem"
              >
                选择项目：
              </div>
              <div class="select">
                <select
                  v-model="params.projects2"
                  style="font-size: 1.322951rem"
                  @change="projectData($event)"
                >
                  <option
                    style="background-color: unset; color: #5be9ff"
                    v-for="(item, index) in projects"
                    :key="index"
                    :value="item.communityId"
                  >
                    {{ item.communityName }}
                  </option>
                </select>
                <!-- <div style="line-height: 2.4rem; " >
                  <img src="../../assets/largeScreen/Union@2@2x.png" alt="" />
                </div> -->
              </div>
            </div>
          </div>
          <!-- 中间右侧 -->
          <div class="center_right">
            <!-- 出入口人员信息 -->
            <div class="tidings_left">
              <marquee
                direction="up"
                style="text-align: center; height: 100%"
                scrolldelay="500"
              >
                <div
                  class="personnel"
                  v-for="(item, index) in personnels"
                  :key="index"
                >
                  <!-- <div class="photo">
                    <img
                      :src="item.picUrl"
                      alt=""
                      style="width: 120%; height: 90%"
                    />
                  </div> -->
                  <div class="Information">
                    <div class="Information_left">
                      <div style="color: #62f7f9; font-size: 1.22rem">
                        {{ item.personName }}
                      </div>
                      <div style="color: #9fadbc">{{ item.communityName }}</div>
                    </div>
                    <div class="Information_right">
                      <div
                        class="identity"
                        style="text-align: right; height: 1.2rem"
                      >
                        <div
                          style="
                            height: 1.2rem;
                            line-height: 1.3rem;
                            margin-left: 0.6rem;
                            color: #bd620f;
                          "
                          v-if="item.inAndOutType == 1"
                        >
                          进
                        </div>

                        <div
                          style="
                            height: 1.2rem;
                            line-height: 1.3rem;
                            margin-left: 0.6rem;
                            color: #028d20;
                          "
                          v-if="item.inAndOutType == 0"
                        >
                          出
                        </div>
                      </div>
                      <div style="color: #9fadbc">{{ item.doorName }}</div>
                      <div class="time" style="color: #9fadbc">
                        <span>{{ item.receiveTime }}</span>
                        <!-- <span style="margin-left: .6rem;">12:21:00</span> -->
                      </div>
                    </div>
                  </div>
                </div>
              </marquee>

              <!-- <marquee
                    direction="up"
                    style="text-align: center;height:70%"
                    scrolldelay="500"
                  > -->
            </div>
          </div>
        </div>
        <!-- 右侧大屏 -->
        <div class="largeScreen_right">
          <el-row class="box_head">
            <!-- 各设备统计 -->
            <el-col :span="6" class="equipment_01">
              <div ref="equipment" style="width: 100%; height: 100%"></div>
              <div class="equipment_name">
                <div class="equipment_name_01 white">
                  <div class="f" style="margin-bottom: 1rem">
                    <div class="f">
                      <div
                        style="
                          width: 0.8rem;
                          height: 0.8rem;
                          background: #1890ff;
                        "
                      ></div>
                      <div
                        style="
                          margin: -0.15rem 1rem 0 0.5rem;
                          font-size: 0.8rem;
                        "
                      >
                        监控
                      </div>
                    </div>
                    <div class="f">
                      <div
                        style="
                          width: 0.8rem;
                          height: 0.8rem;
                          background: #13b68d;
                        "
                      ></div>
                      <div
                        style="
                          margin: -0.15rem 1rem 0 0.5rem;
                          font-size: 0.8rem;
                        "
                      >
                        电梯
                      </div>
                    </div>
                    <div class="f">
                      <div
                        style="
                          width: 0.8rem;
                          height: 0.8rem;
                          background: #b8b559;
                        "
                      ></div>
                      <div
                        style="margin: -0.15rem 0 0 0.5rem; font-size: 0.8rem"
                      >
                        道闸
                      </div>
                    </div>
                  </div>
                  <div class="f white">
                    <div class="f">
                      <div
                        style="
                          width: 0.8rem;
                          height: 0.8rem;
                          background: #64ba65;
                        "
                      ></div>
                      <div
                        style="
                          margin: -0.15rem 1rem 0 0.5rem;
                          font-size: 0.8rem;
                        "
                      >
                        单元门可视
                      </div>
                    </div>
                    <div class="f">
                      <div
                        style="
                          width: 0.8rem;
                          height: 0.8rem;
                          background: #47ffff;
                        "
                      ></div>
                      <div
                        style="
                          margin: -0.15rem 1rem 0 0.5rem;
                          font-size: 0.8rem;
                        "
                      >
                        消防传感器
                      </div>
                    </div>
                    <div class="f">
                      <div
                        style="
                          width: 0.8rem;
                          height: 0.8rem;
                          background: #4ba5ff;
                        "
                      ></div>
                      <div
                        style="margin: -0.15rem 0 0 0.5rem; font-size: 0.8rem"
                      >
                        门禁
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="modular white">
                <div class="f" style="margin-top: 2rem">
                  <div class="f ai-c">
                    <div
                      style="
                        width: 1.6rem;
                        height: 1.6rem;
                        margin-right: 0.8rem;
                      "
                    >
                      <img
                        style="width: 100%; height: 100%"
                        src="../../assets/largeScreen/jiankong.png"
                        alt=""
                      />
                    </div>
                    <div>监控：</div>
                    <div style="color: #47ffff">
                      {{ barrier.camera || "0" }}
                    </div>
                  </div>
                  <div class="f ai-c" style="margin-left: 1.5rem">
                    <div
                      style="
                        width: 1.6rem;
                        height: 1.6rem;
                        margin-right: 0.8rem;
                      "
                    >
                      <img
                        style="width: 100%; height: 100%"
                        src="../../assets/largeScreen/dianti.png"
                        alt=""
                      />
                    </div>
                    <div>电梯：</div>
                    <div style="color: #47ffff">
                      {{ barrier.elevator || "0" }}
                    </div>
                  </div>
                </div>
                <div class="f" style="margin-top: 2rem">
                  <div class="f ai-c">
                    <div
                      style="
                        width: 1.6rem;
                        height: 1.6rem;
                        margin-right: 0.8rem;
                      "
                    >
                      <img
                        style="width: 100%; height: 100%"
                        src="../../assets/largeScreen/daozha.png"
                        alt=""
                      />
                    </div>
                    <div>道闸：</div>
                    <div style="color: #47ffff">
                      {{ barrier.barrier || "0" }}
                    </div>
                  </div>
                  <div class="f ai-c" style="margin-left: 1.5rem">
                    <div
                      style="
                        width: 1.6rem;
                        height: 1.6rem;
                        margin-right: 0.8rem;
                      "
                    >
                      <img
                        style="width: 100%; height: 100%"
                        src="../../assets/largeScreen/zhaoming.png"
                        alt=""
                      />
                    </div>
                    <div>单元门可视：</div>
                    <div style="color: #47ffff">
                      {{ barrier.visual || "0" }}
                    </div>
                  </div>
                </div>
                <div class="f" style="margin-top: 2rem">
                  <div class="f ai-c">
                    <div
                      style="
                        width: 1.6rem;
                        height: 1.6rem;
                        margin-right: 0.8rem;
                      "
                    >
                      <img
                        style="width: 100%; height: 100%"
                        src="../../assets/largeScreen/guangbo.png"
                        alt=""
                      />
                    </div>
                    <div>门禁：</div>
                    <div style="color: #47ffff">
                      {{ barrier.access || "0" }}
                    </div>
                  </div>
                  <div class="f ai-c" style="margin-left: 1.5rem">
                    <div
                      style="
                        width: 1.6rem;
                        height: 1.6rem;
                        margin-right: 0.8rem;
                      "
                    >
                      <img
                        style="width: 100%; height: 100%"
                        src="../../assets/largeScreen/menjin.png"
                        alt=""
                      />
                    </div>
                    <div>消防传感器：</div>
                    <div style="color: #47ffff">
                      {{ barrier.sensor || "0" }}
                    </div>
                  </div>
                </div>
              </div>
            </el-col>
            <!-- 小区设备告警统计 -->
            <el-col :span="6" class="warning_01">
              <div
                ref="allocation"
                style="width: 112%; height: 100%; margin-left: -0.8rem"
              ></div>
              <!-- <el-row>
                <el-col :span="7"
                  ><div
                    ref="warning"
                    style="width: 110%; height: 160%; margin: 2rem 0 0 0.5rem"
                  ></div>
                  <div style="margin: -2rem 0 0 5rem; color: #31c086">
                    安全
                  </div></el-col
                >
                <el-col :span="7"
                  ><div
                    ref="warning1"
                    style="width: 110%; height: 160%; margin: 2rem 0 0 1.5rem"
                  ></div>
                  <div style="margin: -2rem 0 0 6rem; color: #31c086">
                    安全
                  </div></el-col
                >
                <el-col :span="7"
                  ><div
                    ref="warning2"
                    style="width: 110%; height: 160%; margin: 2rem 0 0 2.5rem"
                  ></div>
                  <div style="margin: -2rem 0 0 7rem; color: #31c086">
                    安全
                  </div></el-col
                >
              </el-row> -->
            </el-col>
            <!-- 设备异常列表 -->
            <el-col
              :span="6"
              class="abnormal_01"
              style="text-align: center; color: #01a5a8"
            >
              <el-row
                style="
                  margin-top: 4rem;
                  background-image: linear-gradient(
                    to right,
                    rgba(2, 59, 82, 0.1),
                    rgba(71, 255, 255, 0.1)
                  );
                "
              >
                <el-col :span="4">名称</el-col>
                <el-col :span="11">设备名称</el-col>
                <el-col :span="9">时间</el-col>
              </el-row>
              <marquee
                direction="up"
                style="text-align: center; height: 70%"
                scrolldelay="500"
              >
                <el-row
                  style="margin: 0.9rem 0 0 0rem"
                  v-for="(item, index) in device"
                  :key="index"
                >
                  <el-col :span="4">{{ item.name }}</el-col>
                  <el-col :span="11">{{
                    item.address == null
                      ? "暂无"
                      : item.address == " "
                      ? "暂无"
                      : item.address
                  }}</el-col>
                  <el-col :span="9">{{ item.time }}</el-col>
                  <!-- <el-col :span="9" v-if="item.address != null && item.address != ' '">{{
                    item.time.substr(0, 10)
                  }}</el-col> -->
                  <!-- <el-col
                    :span="20"
                    style="    text-align: right;padding-right: 3.6rem;"
                    v-if="(item.address == null || item.address == ' ') && item.name != null"
                    >{{ item.time.substr(0, 10) }}</el-col
                  >
                  <el-col
                    :span="24"
                    style="    text-align: right;padding-right: 3.6rem;"
                    v-if="item.name == null"
                    >{{ item.time.substr(0, 10) }}</el-col
                  > -->
                </el-row>
              </marquee>
            </el-col>

            <!-- <el-col
              :span="6"
              class="abnormal_01"
              style="text-align: center; color: #01a5a8"
            >
              <el-row
                style="
                  margin-top: 4rem;
                  background-image: linear-gradient(
                    to right,
                    rgba(2,59,82, .1),
                    rgba(71,255,255, .1)
                  );
                "
              >
                <el-col :span="4">名称</el-col>
                <el-col :span="11">设备地址</el-col>
                <el-col :span="9">离线时间</el-col>
              </el-row>

              <marquee
                direction="up"
                style="text-align: right;height:70%"
                scrolldelay="500"
              >
                <el-row
                  style="margin: 0.9rem 0 0 0rem"
                  v-for="(item,index) in device"
                  :key="index"
                >



                  <el-col :span="4" >{{item.name}}</el-col>
                  <el-col :span="20" style="padding-right: 3.6rem;">{{item.time.substr(0,10)}}</el-col>

                </el-row>
              </marquee>

            </el-col> -->
            <!-- 工单统计 -->
            <el-col :span="6" class="statistics_state">
              <el-row>
                <el-col
                  :span="12"
                  style="width: 16rem; height: 15rem; margin-top: 1.5rem"
                >
                  <div
                    style="width: 100%; height: 100%"
                    ref="stateStatistics"
                  ></div>
                </el-col>
              </el-row>
              <el-row style="position: absolute; top: 0rem; right: -2rem">
                <el-col
                  :span="12"
                  style="width: 16rem; height: 15rem; margin-top: 1.5rem"
                >
                  <div
                    ref="repairReport"
                    style="width: 100%; height: 100%"
                  ></div>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
          <el-row class="box_center">
            <!-- 一键处理申请 -->
            <el-col :span="6" class="statistics_apply">
              <el-row>
                <el-col>
                  <el-col :span="5" class="apply_01">
                    <el-row>
                      <el-row>今日处理事件：</el-row>
                      <el-row class="typeface">
                        <el-col
                          :span="18"
                          style="text-align: right; font-size: 3rem"
                          >{{ totalData.dealCount }}</el-col
                        >
                        <el-col style="margin-top: 1.5rem" :offset="2" :span="4"
                          >件</el-col
                        >
                      </el-row>
                    </el-row>
                    <el-row class="apply_02">
                      <el-row>待处理事件：</el-row>
                      <el-row class="typeface">
                        <el-col
                          :span="18"
                          style="
                            text-align: right;
                            font-size: 3rem;
                            color: #ce060a;
                          "
                          >{{ totalData.notDealCount }}</el-col
                        >
                        <el-col
                          style="margin-top: 1.5rem; color: #967138"
                          :offset="2"
                          :span="4"
                          >件</el-col
                        >
                      </el-row>
                    </el-row>
                  </el-col>
                  <el-col :span="6" style="" class="apply_03">
                    <el-row style="height: 8rem">
                      <div style="width: 100%; height: 100%" ref="applye"></div>
                    </el-row>
                    <el-row>
                      <el-col>
                        <el-row>总计处理事件：</el-row>
                        <el-row class="typeface">
                          <el-col
                            :span="18"
                            style="text-align: right; font-size: 3rem"
                            >{{ totalData.totalCount }}</el-col
                          >
                          <el-col
                            style="margin-top: 1.5rem"
                            :offset="2"
                            :span="4"
                            >件</el-col
                          >
                        </el-row>
                      </el-col>
                    </el-row>
                  </el-col>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="6" class="work_order">
              <el-col
                :span="4"
                class="typeface"
                style="margin: 7.2rem 0 0 3.2rem; text-align: center"
              >
                <el-row style="font-size: 2.5rem">{{ totalNumber }}</el-row>
                <!-- <el-row
                  style="
                    font-size: 0.8rem;
                    margin: 5.1rem 0 0rem;
                    text-align: right;
                  "
                  >年度工单总数</el-row
                > -->
              </el-col>
              <el-col :span="15" style="margin: 4rem 0 0 1rem; height: 11.5rem">
                <div ref="statistics" style="width: 110%; height: 110%"></div>
              </el-col>
            </el-col>
            <!-- 年度维修设备统计 -->
            <el-col :span="6" class="statistics_repair">
              <div class="number" style="">
                <!-- 社区人行下拉框 -->
                <!-- <span style="color:#5BE9FF;margin-left: 2.4rem;">{{one}}年</span> -->
                <!-- <select
                  v-model="month5"
                  style="
                    margin-top: -0.2rem;
                    margin-left: 13.3rem;
                    outline: none;
                    border: 0;
                    background-color: unset;
                    color: #5be9ff;
                    z-index: 999;
                  "
                  @change="getMaintenance()"
                >
                  <option
                    v-for="x in month6"
                    style="background-color: unset; color: #5be9ff"
                    :value="x.name"
                  >
                    {{ x.name }}年
                  </option>
                </select> -->

                <el-select
                  v-model="month5"
                  placeholder="请选择"
                  @change="getMaintenance()"
                  :popper-append-to-body="false"
                  style="
                    margin-top: -0.2rem;
                    margin-left: 11.3rem;
                    outline: none;
                    border: 0;
                    background-color: unset;
                    color: #5be9ff;
                    z-index: 999;
                  "
                >
                  <el-option
                    v-for="item in month6"
                    :key="item.name"
                    :label="item.name"
                    :value="item.name"
                  >
                    {{ item.name }}年
                  </el-option>
                </el-select>
              </div>
              <div
                ref="repairAnnual"
                style="width: 100%; height: 88%; margin-top: 2.3rem"
              ></div>
            </el-col>
            <!-- 社区论坛 -->
            <!-- <el-col :span="6" class="statistics_forum">
              <div
                ref="forum"
                style="
                  width: 95%;
                  height: 80%;
                  margin: 2.2rem 0.5rem 0.5rem 1rem;
                "
              ></div>
            </el-col> -->
            <!-- 环境监测底部 -->
            <el-col :span="6" class="environment_bottom font_family">
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
                style="width: 100%; height: 70%; margin-top: 4.9rem"
              ></div>
            </el-col>
          </el-row>
          <el-row class="box_foot">
            <!-- 通知公告 -->
            <el-col :span="6" class="smart" style="">
              <div class="selectFlex">
                <el-select
                  v-model="valueSmart"
                  placeholder="请选择"
                  @change="chooseSmart()"
                  :popper-append-to-body="false"
                >
                  <el-option
                    v-for="item in optionsSmart"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </div>
              <el-table
                v-if="valueSmart == 1"
                :data="selectNotice"
                class="selectNoticeClass"
              >
                <el-table-column
                  prop="noticeTitle"
                  label="标题"
                  align="center"
                  show-overflow-tooltip
                >
                </el-table-column>
                <el-table-column
                  prop="range"
                  align="center"
                  label="发布范围"
                  show-overflow-tooltip
                >
                  <template slot-scope="scope">
                    <span>{{
                      scope.row.range == 0
                        ? "全部"
                        : scope.row.range == 1
                        ? "物业"
                        : "业主"
                    }}</span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="createTime"
                  label="发布时间"
                  align="center"
                  show-overflow-tooltip
                >
                </el-table-column>
                <el-table-column
                  prop="noticeStatus"
                  label="状态"
                  align="center"
                  show-overflow-tooltip
                >
                  <template slot-scope="scope">
                    <span v-if="scope.row.noticeStatus == 0">已发布</span>
                    <span v-if="scope.row.noticeStatus == 1" class="orgner"
                      >已撤回</span
                    >
                  </template>
                </el-table-column>
                <el-table-column
                  prop="readNum"
                  label="阅读数"
                  align="center"
                  show-overflow-tooltip
                >
                </el-table-column>
              </el-table>
              <el-table
                v-if="valueSmart == 2"
                :data="selectInvest"
                class="selectNoticeClass"
              >
                <el-table-column
                  prop="investTitle"
                  label="标题"
                  align="center"
                  show-overflow-tooltip
                >
                </el-table-column>
                <el-table-column
                  prop="communityName"
                  align="center"
                  label="所属小区"
                  show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                  prop="createTime"
                  label="发布时间"
                  align="center"
                  show-overflow-tooltip
                >
                </el-table-column>
                <el-table-column
                  prop="investStatus"
                  label="状态"
                  align="center"
                  show-overflow-tooltip
                >
                  <template slot-scope="scope">
                    <span v-if="scope.row.investStatus == 0">已发布</span>
                    <span v-if="scope.row.investStatus == 1" class="orgner"
                      >已撤回</span
                    >
                  </template>
                </el-table-column>
                <el-table-column
                  prop="readNum"
                  label="阅读数"
                  align="center"
                  show-overflow-tooltip
                >
                </el-table-column>
              </el-table>
            </el-col>
            <!-- 巡查 巡更 巡检状况 -->
            <el-col :span="12" class="tidings_right">
              <el-row class="flow_traffic">
                <el-col :span="12">
                  <el-col :span="24" style="">
                    <el-row
                      style="color: #01bebf; margin: 4.7rem 0 0.8rem 3.5rem"
                      >7日巡查状况</el-row
                    >
                    <el-row
                      style="margin: 0.5rem 0 0.8rem 3rem; text-align: center"
                    >
                      <el-col
                        :span="3"
                        style="margin: 0.5rem 0 0.25rem 0.25rem"
                        v-for="i in 7"
                        :key="i.id"
                      >
                        <el-row v-if="i == '1'">
                          <!-- <el-row :span="24" style="">
                            <img
                              :src="i > 4 ? inactive : active"
                              alt=""
                              style="width:100%;height:5rem"
                            />
                          </el-row> -->
                          <el-row :span="24" style="">
                            <img
                              :src="inactive1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '一' &&
                                visitorsTrafficPatrol.sevenDay != '0'
                              "
                            />
                            <img
                              :src="inactive2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '二' &&
                                visitorsTrafficPatrol.sevenDay != '0'
                              "
                            />
                            <img
                              :src="inactive3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '三' &&
                                visitorsTrafficPatrol.sevenDay != '0'
                              "
                            />
                            <img
                              :src="inactive4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '四' &&
                                visitorsTrafficPatrol.sevenDay != '0'
                              "
                            />
                            <img
                              :src="inactive5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '五' &&
                                visitorsTrafficPatrol.sevenDay != '0'
                              "
                            />
                            <img
                              :src="inactive6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '六' &&
                                visitorsTrafficPatrol.sevenDay != '0'
                              "
                            />
                            <img
                              :src="inactive7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '日' &&
                                visitorsTrafficPatrol.sevenDay != '0'
                              "
                            />

                            <img
                              :src="active1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '一' &&
                                visitorsTrafficPatrol.sevenDay == '0'
                              "
                            />
                            <img
                              :src="active2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '二' &&
                                visitorsTrafficPatrol.sevenDay == '0'
                              "
                            />
                            <img
                              :src="active3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '三' &&
                                visitorsTrafficPatrol.sevenDay == '0'
                              "
                            />
                            <img
                              :src="active4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '四' &&
                                visitorsTrafficPatrol.sevenDay == '0'
                              "
                            />
                            <img
                              :src="active5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '五' &&
                                visitorsTrafficPatrol.sevenDay == '0'
                              "
                            />
                            <img
                              :src="active6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '六' &&
                                visitorsTrafficPatrol.sevenDay == '0'
                              "
                            />
                            <img
                              :src="active7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '日' &&
                                visitorsTrafficPatrol.sevenDay == '0'
                              "
                            />
                          </el-row>
                          <el-row :span="24" style="color: #3fe2e6">{{
                            sevenList
                          }}</el-row>
                        </el-row>
                        <el-row v-if="i == '2'">
                          <el-row :span="24" style="">
                            <img
                              :src="inactive1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '一' &&
                                visitorsTrafficPatrol.sixDay != '0'
                              "
                            />
                            <img
                              :src="inactive2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '二' &&
                                visitorsTrafficPatrol.sixDay != '0'
                              "
                            />
                            <img
                              :src="inactive3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '三' &&
                                visitorsTrafficPatrol.sixDay != '0'
                              "
                            />
                            <img
                              :src="inactive4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '四' &&
                                visitorsTrafficPatrol.sixDay != '0'
                              "
                            />
                            <img
                              :src="inactive5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '五' &&
                                visitorsTrafficPatrol.sixDay != '0'
                              "
                            />
                            <img
                              :src="inactive6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '六' &&
                                visitorsTrafficPatrol.sixDay != '0'
                              "
                            />
                            <img
                              :src="inactive7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '日' &&
                                visitorsTrafficPatrol.sixDay != '0'
                              "
                            />

                            <img
                              :src="active1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '一' &&
                                visitorsTrafficPatrol.sixDay == '0'
                              "
                            />
                            <img
                              :src="active2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '二' &&
                                visitorsTrafficPatrol.sixDay == '0'
                              "
                            />
                            <img
                              :src="active3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '三' &&
                                visitorsTrafficPatrol.sixDay == '0'
                              "
                            />
                            <img
                              :src="active4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '四' &&
                                visitorsTrafficPatrol.sixDay == '0'
                              "
                            />
                            <img
                              :src="active5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '五' &&
                                visitorsTrafficPatrol.sixDay == '0'
                              "
                            />
                            <img
                              :src="active6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '六' &&
                                visitorsTrafficPatrol.sixDay == '0'
                              "
                            />
                            <img
                              :src="active7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '日' &&
                                visitorsTrafficPatrol.sixDay == '0'
                              "
                            />
                          </el-row>
                          <el-row :span="24" style="color: #3fe2e6">{{
                            sixList
                          }}</el-row>
                        </el-row>
                        <el-row v-if="i == '3'">
                          <el-row :span="24" style="">
                            <img
                              :src="inactive1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '一' &&
                                visitorsTrafficPatrol.fiveDay != '0'
                              "
                            />
                            <img
                              :src="inactive2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '二' &&
                                visitorsTrafficPatrol.fiveDay != '0'
                              "
                            />
                            <img
                              :src="inactive3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '三' &&
                                visitorsTrafficPatrol.fiveDay != '0'
                              "
                            />
                            <img
                              :src="inactive4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '四' &&
                                visitorsTrafficPatrol.fiveDay != '0'
                              "
                            />
                            <img
                              :src="inactive5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '五' &&
                                visitorsTrafficPatrol.fiveDay != '0'
                              "
                            />
                            <img
                              :src="inactive6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '六' &&
                                visitorsTrafficPatrol.fiveDay != '0'
                              "
                            />
                            <img
                              :src="inactive7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '日' &&
                                visitorsTrafficPatrol.fiveDay != '0'
                              "
                            />

                            <img
                              :src="active1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '一' &&
                                visitorsTrafficPatrol.fiveDay == '0'
                              "
                            />
                            <img
                              :src="active2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '二' &&
                                visitorsTrafficPatrol.fiveDay == '0'
                              "
                            />
                            <img
                              :src="active3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '三' &&
                                visitorsTrafficPatrol.fiveDay == '0'
                              "
                            />
                            <img
                              :src="active4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '四' &&
                                visitorsTrafficPatrol.fiveDay == '0'
                              "
                            />
                            <img
                              :src="active5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '五' &&
                                visitorsTrafficPatrol.fiveDay == '0'
                              "
                            />
                            <img
                              :src="active6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '六' &&
                                visitorsTrafficPatrol.fiveDay == '0'
                              "
                            />
                            <img
                              :src="active7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '日' &&
                                visitorsTrafficPatrol.fiveDay == '0'
                              "
                            />
                          </el-row>
                          <el-row :span="24" style="color: #3fe2e6">{{
                            fiveList
                          }}</el-row>
                        </el-row>
                        <el-row v-if="i == '4'">
                          <el-row :span="24" style="">
                            <img
                              :src="inactive1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '一' &&
                                visitorsTrafficPatrol.fourDay != '0'
                              "
                            />
                            <img
                              :src="inactive2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '二' &&
                                visitorsTrafficPatrol.fourDay != '0'
                              "
                            />
                            <img
                              :src="inactive3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '三' &&
                                visitorsTrafficPatrol.fourDay != '0'
                              "
                            />
                            <img
                              :src="inactive4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '四' &&
                                visitorsTrafficPatrol.fourDay != '0'
                              "
                            />
                            <img
                              :src="inactive5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '五' &&
                                visitorsTrafficPatrol.fourDay != '0'
                              "
                            />
                            <img
                              :src="inactive6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '六' &&
                                visitorsTrafficPatrol.fourDay != '0'
                              "
                            />
                            <img
                              :src="inactive7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '日' &&
                                visitorsTrafficPatrol.fourDay != '0'
                              "
                            />

                            <img
                              :src="active1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '一' &&
                                visitorsTrafficPatrol.fourDay == '0'
                              "
                            />
                            <img
                              :src="active2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '二' &&
                                visitorsTrafficPatrol.fourDay == '0'
                              "
                            />
                            <img
                              :src="active3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '三' &&
                                visitorsTrafficPatrol.fourDay == '0'
                              "
                            />
                            <img
                              :src="active4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '四' &&
                                visitorsTrafficPatrol.fourDay == '0'
                              "
                            />
                            <img
                              :src="active5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '五' &&
                                visitorsTrafficPatrol.fourDay == '0'
                              "
                            />
                            <img
                              :src="active6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '六' &&
                                visitorsTrafficPatrol.fourDay == '0'
                              "
                            />
                            <img
                              :src="active7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '日' &&
                                visitorsTrafficPatrol.fourDay == '0'
                              "
                            />
                          </el-row>
                          <el-row :span="24" style="color: #3fe2e6">{{
                            fourList
                          }}</el-row>
                        </el-row>
                        <el-row v-if="i == '5'">
                          <el-row :span="24" style="">
                            <img
                              :src="inactive1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '一' &&
                                visitorsTrafficPatrol.threeDay != '0'
                              "
                            />
                            <img
                              :src="inactive2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '二' &&
                                visitorsTrafficPatrol.threeDay != '0'
                              "
                            />
                            <img
                              :src="inactive3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '三' &&
                                visitorsTrafficPatrol.threeDay != '0'
                              "
                            />
                            <img
                              :src="inactive4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '四' &&
                                visitorsTrafficPatrol.threeDay != '0'
                              "
                            />
                            <img
                              :src="inactive5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '五' &&
                                visitorsTrafficPatrol.threeDay != '0'
                              "
                            />
                            <img
                              :src="inactive6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '六' &&
                                visitorsTrafficPatrol.threeDay != '0'
                              "
                            />
                            <img
                              :src="inactive7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '日' &&
                                visitorsTrafficPatrol.threeDay != '0'
                              "
                            />

                            <img
                              :src="active1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '一' &&
                                visitorsTrafficPatrol.threeDay == '0'
                              "
                            />
                            <img
                              :src="active2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '二' &&
                                visitorsTrafficPatrol.threeDay == '0'
                              "
                            />
                            <img
                              :src="active3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '三' &&
                                visitorsTrafficPatrol.threeDay == '0'
                              "
                            />
                            <img
                              :src="active4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '四' &&
                                visitorsTrafficPatrol.threeDay == '0'
                              "
                            />
                            <img
                              :src="active5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '五' &&
                                visitorsTrafficPatrol.threeDay == '0'
                              "
                            />
                            <img
                              :src="active6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '六' &&
                                visitorsTrafficPatrol.threeDay == '0'
                              "
                            />
                            <img
                              :src="active7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '日' &&
                                visitorsTrafficPatrol.threeDay == '0'
                              "
                            />
                          </el-row>
                          <el-row :span="24" style="color: #3fe2e6">{{
                            threeList
                          }}</el-row>
                        </el-row>
                        <el-row v-if="i == '6'">
                          <el-row :span="24" style="">
                            <img
                              :src="inactive1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '一' &&
                                visitorsTrafficPatrol.yesterday != '0'
                              "
                            />
                            <img
                              :src="inactive2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '二' &&
                                visitorsTrafficPatrol.yesterday != '0'
                              "
                            />
                            <img
                              :src="inactive3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '三' &&
                                visitorsTrafficPatrol.yesterday != '0'
                              "
                            />
                            <img
                              :src="inactive4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '四' &&
                                visitorsTrafficPatrol.yesterday != '0'
                              "
                            />
                            <img
                              :src="inactive5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '五' &&
                                visitorsTrafficPatrol.yesterday != '0'
                              "
                            />
                            <img
                              :src="inactive6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '六' &&
                                visitorsTrafficPatrol.yesterday != '0'
                              "
                            />
                            <img
                              :src="inactive7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '日' &&
                                visitorsTrafficPatrol.yesterday != '0'
                              "
                            />

                            <img
                              :src="active1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '一' &&
                                visitorsTrafficPatrol.yesterday == '0'
                              "
                            />
                            <img
                              :src="active2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '二' &&
                                visitorsTrafficPatrol.yesterday == '0'
                              "
                            />
                            <img
                              :src="active3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '三' &&
                                visitorsTrafficPatrol.yesterday == '0'
                              "
                            />
                            <img
                              :src="active4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '四' &&
                                visitorsTrafficPatrol.yesterday == '0'
                              "
                            />
                            <img
                              :src="active5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '五' &&
                                visitorsTrafficPatrol.yesterday == '0'
                              "
                            />
                            <img
                              :src="active6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '六' &&
                                visitorsTrafficPatrol.yesterday == '0'
                              "
                            />
                            <img
                              :src="active7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '日' &&
                                visitorsTrafficPatrol.yesterday == '0'
                              "
                            />
                          </el-row>
                          <el-row :span="24" style="color: #3fe2e6">{{
                            twoList
                          }}</el-row>
                        </el-row>
                        <el-row v-if="i == '7'">
                          <el-row :span="24" style="">
                            <img
                              :src="inactive1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '一' &&
                                visitorsTrafficPatrol.toDay != '0'
                              "
                            />
                            <img
                              :src="inactive2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '二' &&
                                visitorsTrafficPatrol.toDay != '0'
                              "
                            />
                            <img
                              :src="inactive3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '三' &&
                                visitorsTrafficPatrol.toDay != '0'
                              "
                            />
                            <img
                              :src="inactive4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '四' &&
                                visitorsTrafficPatrol.toDay != '0'
                              "
                            />
                            <img
                              :src="inactive5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '五' &&
                                visitorsTrafficPatrol.toDay != '0'
                              "
                            />
                            <img
                              :src="inactive6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '六' &&
                                visitorsTrafficPatrol.toDay != '0'
                              "
                            />
                            <img
                              :src="inactive7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '日' &&
                                visitorsTrafficPatrol.toDay != '0'
                              "
                            />

                            <img
                              :src="active1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '一' &&
                                visitorsTrafficPatrol.toDay == '0'
                              "
                            />
                            <img
                              :src="active2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '二' &&
                                visitorsTrafficPatrol.toDay == '0'
                              "
                            />
                            <img
                              :src="active3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '三' &&
                                visitorsTrafficPatrol.toDay == '0'
                              "
                            />
                            <img
                              :src="active4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '四' &&
                                visitorsTrafficPatrol.toDay == '0'
                              "
                            />
                            <img
                              :src="active5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '五' &&
                                visitorsTrafficPatrol.toDay == '0'
                              "
                            />
                            <img
                              :src="active6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '六' &&
                                visitorsTrafficPatrol.toDay == '0'
                              "
                            />
                            <img
                              :src="active7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '日' &&
                                visitorsTrafficPatrol.toDay == '0'
                              "
                            />
                          </el-row>
                          <el-row :span="24" style="color: #3fe2e6">{{
                            oneList
                          }}</el-row>
                        </el-row>
                      </el-col>
                    </el-row>
                  </el-col>
                  <el-col :span="24" style="">
                    <el-row style="color: #01bebf; margin: 0rem 0 0.8rem 3.5rem"
                      >7日巡更状况</el-row
                    >
                    <el-row
                      style="margin: 0.5rem 0 0.8rem 3rem; text-align: center"
                    >
                      <el-col
                        :span="3"
                        style="margin: 0.5rem 0 0.25rem 0.25rem"
                        v-for="i in 7"
                        :key="i.id"
                      >
                        <el-row v-if="i == '1'">
                          <el-row :span="24" style="">
                            <img
                              :src="inactive1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '一' &&
                                visitorsTraffic.sevenDay != '0'
                              "
                            />
                            <img
                              :src="inactive2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '二' &&
                                visitorsTraffic.sevenDay != '0'
                              "
                            />
                            <img
                              :src="inactive3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '三' &&
                                visitorsTraffic.sevenDay != '0'
                              "
                            />
                            <img
                              :src="inactive4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '四' &&
                                visitorsTraffic.sevenDay != '0'
                              "
                            />
                            <img
                              :src="inactive5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '五' &&
                                visitorsTraffic.sevenDay != '0'
                              "
                            />
                            <img
                              :src="inactive6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '六' &&
                                visitorsTraffic.sevenDay != '0'
                              "
                            />
                            <img
                              :src="inactive7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '日' &&
                                visitorsTraffic.sevenDay != '0'
                              "
                            />

                            <img
                              :src="active1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '一' &&
                                visitorsTraffic.sevenDay == '0'
                              "
                            />
                            <img
                              :src="active2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '二' &&
                                visitorsTraffic.sevenDay == '0'
                              "
                            />
                            <img
                              :src="active3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '三' &&
                                visitorsTraffic.sevenDay == '0'
                              "
                            />
                            <img
                              :src="active4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '四' &&
                                visitorsTraffic.sevenDay == '0'
                              "
                            />
                            <img
                              :src="active5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '五' &&
                                visitorsTraffic.sevenDay == '0'
                              "
                            />
                            <img
                              :src="active6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '六' &&
                                visitorsTraffic.sevenDay == '0'
                              "
                            />
                            <img
                              :src="active7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sevenDate == '日' &&
                                visitorsTraffic.sevenDay == '0'
                              "
                            />
                          </el-row>
                          <el-row :span="24" style="color: #3fe2e6">{{
                            sevenList
                          }}</el-row>
                        </el-row>
                        <el-row v-if="i == '2'">
                          <el-row :span="24" style="">
                            <img
                              :src="inactive1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '一' && visitorsTraffic.sixDay != '0'
                              "
                            />
                            <img
                              :src="inactive2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '二' && visitorsTraffic.sixDay != '0'
                              "
                            />
                            <img
                              :src="inactive3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '三' && visitorsTraffic.sixDay != '0'
                              "
                            />
                            <img
                              :src="inactive4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '四' && visitorsTraffic.sixDay != '0'
                              "
                            />
                            <img
                              :src="inactive5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '五' && visitorsTraffic.sixDay != '0'
                              "
                            />
                            <img
                              :src="inactive6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '六' && visitorsTraffic.sixDay != '0'
                              "
                            />
                            <img
                              :src="inactive7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '日' && visitorsTraffic.sixDay != '0'
                              "
                            />

                            <img
                              :src="active1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '一' && visitorsTraffic.sixDay == '0'
                              "
                            />
                            <img
                              :src="active2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '二' && visitorsTraffic.sixDay == '0'
                              "
                            />
                            <img
                              :src="active3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '三' && visitorsTraffic.sixDay == '0'
                              "
                            />
                            <img
                              :src="active4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '四' && visitorsTraffic.sixDay == '0'
                              "
                            />
                            <img
                              :src="active5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '五' && visitorsTraffic.sixDay == '0'
                              "
                            />
                            <img
                              :src="active6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '六' && visitorsTraffic.sixDay == '0'
                              "
                            />
                            <img
                              :src="active7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                sixDate == '日' && visitorsTraffic.sixDay == '0'
                              "
                            />
                          </el-row>
                          <el-row :span="24" style="color: #3fe2e6">{{
                            sixList
                          }}</el-row>
                        </el-row>
                        <el-row v-if="i == '3'">
                          <el-row :span="24" style="">
                            <img
                              :src="inactive1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '一' &&
                                visitorsTraffic.fiveDay != '0'
                              "
                            />
                            <img
                              :src="inactive2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '二' &&
                                visitorsTraffic.fiveDay != '0'
                              "
                            />
                            <img
                              :src="inactive3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '三' &&
                                visitorsTraffic.fiveDay != '0'
                              "
                            />
                            <img
                              :src="inactive4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '四' &&
                                visitorsTraffic.fiveDay != '0'
                              "
                            />
                            <img
                              :src="inactive5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '五' &&
                                visitorsTraffic.fiveDay != '0'
                              "
                            />
                            <img
                              :src="inactive6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '六' &&
                                visitorsTraffic.fiveDay != '0'
                              "
                            />
                            <img
                              :src="inactive7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '日' &&
                                visitorsTraffic.fiveDay != '0'
                              "
                            />

                            <img
                              :src="active1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '一' &&
                                visitorsTraffic.fiveDay == '0'
                              "
                            />
                            <img
                              :src="active2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '二' &&
                                visitorsTraffic.fiveDay == '0'
                              "
                            />
                            <img
                              :src="active3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '三' &&
                                visitorsTraffic.fiveDay == '0'
                              "
                            />
                            <img
                              :src="active4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '四' &&
                                visitorsTraffic.fiveDay == '0'
                              "
                            />
                            <img
                              :src="active5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '五' &&
                                visitorsTraffic.fiveDay == '0'
                              "
                            />
                            <img
                              :src="active6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '六' &&
                                visitorsTraffic.fiveDay == '0'
                              "
                            />
                            <img
                              :src="active7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fiveDate == '日' &&
                                visitorsTraffic.fiveDay == '0'
                              "
                            />
                          </el-row>
                          <el-row :span="24" style="color: #3fe2e6">{{
                            fiveList
                          }}</el-row>
                        </el-row>
                        <el-row v-if="i == '4'">
                          <el-row :span="24" style="">
                            <img
                              :src="inactive1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '一' &&
                                visitorsTraffic.fourDay != '0'
                              "
                            />
                            <img
                              :src="inactive2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '二' &&
                                visitorsTraffic.fourDay != '0'
                              "
                            />
                            <img
                              :src="inactive3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '三' &&
                                visitorsTraffic.fourDay != '0'
                              "
                            />
                            <img
                              :src="inactive4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '四' &&
                                visitorsTraffic.fourDay != '0'
                              "
                            />
                            <img
                              :src="inactive5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '五' &&
                                visitorsTraffic.fourDay != '0'
                              "
                            />
                            <img
                              :src="inactive6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '六' &&
                                visitorsTraffic.fourDay != '0'
                              "
                            />
                            <img
                              :src="inactive7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '日' &&
                                visitorsTraffic.fourDay != '0'
                              "
                            />

                            <img
                              :src="active1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '一' &&
                                visitorsTraffic.fourDay == '0'
                              "
                            />
                            <img
                              :src="active2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '二' &&
                                visitorsTraffic.fourDay == '0'
                              "
                            />
                            <img
                              :src="active3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '三' &&
                                visitorsTraffic.fourDay == '0'
                              "
                            />
                            <img
                              :src="active4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '四' &&
                                visitorsTraffic.fourDay == '0'
                              "
                            />
                            <img
                              :src="active5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '五' &&
                                visitorsTraffic.fourDay == '0'
                              "
                            />
                            <img
                              :src="active6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '六' &&
                                visitorsTraffic.fourDay == '0'
                              "
                            />
                            <img
                              :src="active7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                fourDate == '日' &&
                                visitorsTraffic.fourDay == '0'
                              "
                            />
                          </el-row>
                          <el-row :span="24" style="color: #3fe2e6">{{
                            fourList
                          }}</el-row>
                        </el-row>
                        <el-row v-if="i == '5'">
                          <el-row :span="24" style="">
                            <img
                              :src="inactive1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '一' &&
                                visitorsTraffic.threeDay != '0'
                              "
                            />
                            <img
                              :src="inactive2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '二' &&
                                visitorsTraffic.threeDay != '0'
                              "
                            />
                            <img
                              :src="inactive3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '三' &&
                                visitorsTraffic.threeDay != '0'
                              "
                            />
                            <img
                              :src="inactive4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '四' &&
                                visitorsTraffic.threeDay != '0'
                              "
                            />
                            <img
                              :src="inactive5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '五' &&
                                visitorsTraffic.threeDay != '0'
                              "
                            />
                            <img
                              :src="inactive6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '六' &&
                                visitorsTraffic.threeDay != '0'
                              "
                            />
                            <img
                              :src="inactive7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '日' &&
                                visitorsTraffic.threeDay != '0'
                              "
                            />

                            <img
                              :src="active1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '一' &&
                                visitorsTraffic.threeDay == '0'
                              "
                            />
                            <img
                              :src="active2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '二' &&
                                visitorsTraffic.threeDay == '0'
                              "
                            />
                            <img
                              :src="active3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '三' &&
                                visitorsTraffic.threeDay == '0'
                              "
                            />
                            <img
                              :src="active4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '四' &&
                                visitorsTraffic.threeDay == '0'
                              "
                            />
                            <img
                              :src="active5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '五' &&
                                visitorsTraffic.threeDay == '0'
                              "
                            />
                            <img
                              :src="active6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '六' &&
                                visitorsTraffic.threeDay == '0'
                              "
                            />
                            <img
                              :src="active7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                threeDate == '日' &&
                                visitorsTraffic.threeDay == '0'
                              "
                            />
                          </el-row>
                          <el-row :span="24" style="color: #3fe2e6">{{
                            threeList
                          }}</el-row>
                        </el-row>
                        <el-row v-if="i == '6'">
                          <el-row :span="24" style="">
                            <img
                              :src="inactive1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '一' &&
                                visitorsTraffic.yesterday != '0'
                              "
                            />
                            <img
                              :src="inactive2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '二' &&
                                visitorsTraffic.yesterday != '0'
                              "
                            />
                            <img
                              :src="inactive3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '三' &&
                                visitorsTraffic.yesterday != '0'
                              "
                            />
                            <img
                              :src="inactive4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '四' &&
                                visitorsTraffic.yesterday != '0'
                              "
                            />
                            <img
                              :src="inactive5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '五' &&
                                visitorsTraffic.yesterday != '0'
                              "
                            />
                            <img
                              :src="inactive6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '六' &&
                                visitorsTraffic.yesterday != '0'
                              "
                            />
                            <img
                              :src="inactive7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '日' &&
                                visitorsTraffic.yesterday != '0'
                              "
                            />

                            <img
                              :src="active1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '一' &&
                                visitorsTraffic.yesterday == '0'
                              "
                            />
                            <img
                              :src="active2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '二' &&
                                visitorsTraffic.yesterday == '0'
                              "
                            />
                            <img
                              :src="active3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '三' &&
                                visitorsTraffic.yesterday == '0'
                              "
                            />
                            <img
                              :src="active4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '四' &&
                                visitorsTraffic.yesterday == '0'
                              "
                            />
                            <img
                              :src="active5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '五' &&
                                visitorsTraffic.yesterday == '0'
                              "
                            />
                            <img
                              :src="active6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '六' &&
                                visitorsTraffic.yesterday == '0'
                              "
                            />
                            <img
                              :src="active7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                twoDate == '日' &&
                                visitorsTraffic.yesterday == '0'
                              "
                            />
                          </el-row>
                          <el-row :span="24" style="color: #3fe2e6">{{
                            twoList
                          }}</el-row>
                        </el-row>
                        <el-row v-if="i == '7'">
                          <el-row :span="24" style="">
                            <img
                              :src="inactive1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '一' && visitorsTraffic.toDay != '0'
                              "
                            />
                            <img
                              :src="inactive2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '二' && visitorsTraffic.toDay != '0'
                              "
                            />
                            <img
                              :src="inactive3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '三' && visitorsTraffic.toDay != '0'
                              "
                            />
                            <img
                              :src="inactive4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '四' && visitorsTraffic.toDay != '0'
                              "
                            />
                            <img
                              :src="inactive5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '五' && visitorsTraffic.toDay != '0'
                              "
                            />
                            <img
                              :src="inactive6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '六' && visitorsTraffic.toDay != '0'
                              "
                            />
                            <img
                              :src="inactive7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '日' && visitorsTraffic.toDay != '0'
                              "
                            />

                            <img
                              :src="active1"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '一' && visitorsTraffic.toDay == '0'
                              "
                            />
                            <img
                              :src="active2"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '二' && visitorsTraffic.toDay == '0'
                              "
                            />
                            <img
                              :src="active3"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '三' && visitorsTraffic.toDay == '0'
                              "
                            />
                            <img
                              :src="active4"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '四' && visitorsTraffic.toDay == '0'
                              "
                            />
                            <img
                              :src="active5"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '五' && visitorsTraffic.toDay == '0'
                              "
                            />
                            <img
                              :src="active6"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '六' && visitorsTraffic.toDay == '0'
                              "
                            />
                            <img
                              :src="active7"
                              alt=""
                              style="width: 100%; height: 5rem"
                              v-if="
                                oneDate == '日' && visitorsTraffic.toDay == '0'
                              "
                            />
                          </el-row>
                          <el-row :span="24" style="color: #3fe2e6">{{
                            oneList
                          }}</el-row>
                        </el-row>
                      </el-col>
                    </el-row>
                  </el-col>
                  <el-col :span="24" style="">
                    <el-row style="color: #01bebf; margin: 0rem 0 0.8rem 3.5rem"
                      >巡检设备统计</el-row
                    >
                    <el-row
                      style="margin: 1.5rem 0 0.8rem 3rem; text-align: center"
                      class="typeface"
                    >
                      <el-col :span="7" style="margin: 0.2rem 0">
                        <el-row>
                          <!-- <el-col
                            :span="24"
                            style="color:#3FE2E6;font-size:.7rem;margin-left:-1.5rem"
                          >
                            昨日巡检设备数：（台）
                          </el-col> -->
                          <el-col
                            :span="24"
                            style="
                              color: #3fe2e6;
                              font-size: 3.5rem;
                              margin: 0 0 0.25rem 0.25rem;
                            "
                            >{{ countPatrolOrder }}</el-col
                          >
                        </el-row>
                      </el-col>
                      <el-col
                        :span="12"
                        style="margin: 0.2rem 0 0.25rem 3.25rem"
                      >
                        <el-row>
                          <!-- <el-col
                            :span="24"
                            style="color:#3FE2E6;font-size:.7rem;margin-left: -3rem"
                          >
                            本月巡检设备数：（台）
                          </el-col> -->
                          <el-col
                            :span="18"
                            style="
                              color: #3fe2e6;
                              font-size: 3.5rem;
                              margin-left: 0.8rem;
                            "
                            >{{ duringMonthPatrolOrder }}</el-col
                          >
                        </el-row>
                      </el-col>
                    </el-row>
                  </el-col>
                </el-col>
                <el-col :span="12" class="white">
                  <el-row style="color: #01bebf; margin: 4.7rem 0 0.8rem 4.5rem"
                    >巡查、巡更、巡检工单列表</el-row
                  >
                  <el-row>
                    <el-col :span="24" class="">
                      <el-row
                        style="
                          text-align: center;
                          margin: 1rem 0 0 0;
                          color: #357f9c;
                        "
                      >
                        <el-col
                          :span="4"
                          style="padding: 0.3rem 0; text-align: right"
                          >分类</el-col
                        >
                        <el-col :span="12" style="padding: 0.3rem 0"
                          >工单名称</el-col
                        >
                        <el-col :span="4" style="padding: 0.3rem 0"
                          >执行人</el-col
                        >
                        <el-col :span="4" style="padding: 0.3rem 0"
                          >状态</el-col
                        >
                      </el-row>
                      <marquee
                        behavior="scroll"
                        onmouseover="this.stop();"
                        onmouseout="this.start();"
                        direction="up"
                        style="text-align: center; height: 21rem"
                        scrolldelay="500"
                      >
                        <el-row
                          class=""
                          v-for="(item, index) in patrolList"
                          :key="index"
                          style="
                            text-align: center;
                            margin: 1.5rem 0 0 0;
                            color: #357f9c;
                          "
                        >
                          <el-col :span="4" style="text-align: right">
                            <span v-if="item.patrolType == 0">巡查</span>
                            <span v-if="item.patrolType == 1">巡检</span>
                            <span v-if="item.patrolType == 2">巡更</span>
                          </el-col>
                          <el-col :span="12">{{
                            item.orderName == "" ? "暂无" : item.orderName
                          }}</el-col>
                          <el-col :span="4">{{
                            item.staffName == null ? "暂无" : item.staffName
                          }}</el-col>
                          <el-col :span="4">
                            <span v-if="item.executeStatus == 0">待执行</span>
                            <span v-if="item.executeStatus == 1">执行中</span>
                            <span v-if="item.executeStatus == 2">已完成</span>
                            <span v-if="item.executeStatus == 3">超时完成</span>
                          </el-col>
                        </el-row>
                      </marquee>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </el-col>
            <!-- 物业排行 物业收费展示 -->
            <el-col :span="6" class="tidings_center">
              <!-- 物业排行 -->
              <el-row class="tidings_flow">
                <el-col
                  class="flow_hand"
                  style="text-align: center; color: #01a5a8"
                >
                  <el-row
                    style="
                      margin-top: 4rem;
                      background-image: linear-gradient(
                        to right,
                        rgba(2, 59, 82, 0.1),
                        rgba(71, 255, 255, 0.1)
                      );
                    "
                  >
                    <el-col :span="4">排名</el-col>
                    <el-col :span="11">物业名称</el-col>
                    <el-col :span="9">综合评分</el-col>
                  </el-row>
                  <marquee
                    direction="up"
                    style="text-align: center; height: 70%"
                    scrolldelay="500"
                  >
                    <el-row
                      style="margin: 0.9rem 0 0 0rem"
                      v-for="(i, j) in Ranking"
                      :key="i.id"
                    >
                      <!-- <el-col :span="4" v-if="i == 1">监控</el-col>
                      <el-col :span="4" v-if="i == 2">道闸</el-col>
                      <el-col :span="4" v-if="i == 3">广播</el-col>
                      <el-col :span="4" v-if="i == 4">电梯</el-col> -->
                      <el-col :span="4">{{ j + 1 }}</el-col>
                      <!-- <el-col :span="11" v-if="i == 1">芳春园1#-1单元</el-col>
                      <el-col :span="11" v-if="i == 2">园区东门入口</el-col>
                      <el-col :span="11" v-if="i == 3"
                        >迎春元4#-4单元门口</el-col
                      >
                      <el-col :span="11" v-if="i == 4">秋月园23#-1单元</el-col> -->
                      <el-col :span="11">{{ i.deptName }}</el-col>
                      <!-- <el-col :span="9" v-if="i == 1">2021-10-14</el-col>
                      <el-col :span="9" v-if="i == 2">2021-10-14</el-col>
                      <el-col :span="9" v-if="i == 3">2021-10-14</el-col>
                      <el-col :span="9" v-if="i == 4">2021-10-14</el-col> -->
                      <el-col :span="9">{{ i.score }}</el-col>
                    </el-row>
                  </marquee>
                </el-col>
              </el-row>
              <!-- 物业收费展示 -->
              <el-row>
                <el-row class="tidings_flow1">
                  <el-col class="flow_foot">
                    <div class="charges_left">
                      <div
                        style="
                          display: flex;
                          justify-content: space-between;
                          height: 1.8rem;
                        "
                      >
                        <div
                          style="
                            color: #48ffff;
                            font-size: 1.82295rem;
                            font-weight: 700;
                          "
                        >
                          {{ charges.parkAmount }}
                        </div>
                        <div style="line-height: 2.5rem; color: #fff">元</div>
                      </div>
                      <div style="color: #48ffff">停车费收取总额</div>
                    </div>
                    <div class="charges_center">
                      <div>
                        <div
                          style="
                            text-align: center;
                            color: #00ff85;
                            font-size: 2.32295rem;
                          "
                        >
                          {{ charges.totalAmount }}
                        </div>
                        <div style="text-align: center; color: #fff">元</div>
                      </div>
                      <div
                        style="
                          margin-top: 1.6rem;
                          text-align: center;
                          color: #00ff85;
                        "
                      >
                        物业收费总额
                      </div>
                    </div>
                    <div class="charges_right">
                      <div
                        style="
                          height: 1.8rem;
                          color: #48ffff;
                          font-size: 1.82295rem;
                          font-weight: 700;
                        "
                      >
                        {{ charges.proportion }}
                      </div>
                      <div style="color: #48ffff">收费比例</div>
                    </div>
                    <!-- <div
                      ref="flow_double"
                      style="width: 100%; height: 100%;"
                    ></div> -->
                  </el-col>
                </el-row>
              </el-row>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getService,
  getRoomStatus,
  getDistribution,
  getCommunitys,
  getCommunitySituation,
  getCommunitycomfort,
  getselectInvest,
  getselectNotice,
  getQueryDaily,
  getQueryYear,
  getEquipment,
  getProcessing,
  getTwoProcessing,
  getWorkOrder,
  getOnlineStatus,
  getPatrolLists,
  getCommunityTraffic,
  getReportRepair,
  getProject,
  getEquipmentInformation,
  getCameraById,
  admissionPersonnel,
  propertyRanking,
  equipmentAlarm,
  inOutHuman,
  annualMaintenance,
  patrolStatus,
  feeDisplay,
  exceptionList,
  getdictData,
  selectWaterNum,
} from "@/api/screen/screen65";
import { areaTreeSelect } from "@/api/system/community/area";
// 注意: echarts高版本中需要使用此方式方可引入
import * as echarts from "echarts";
import "echarts-wordcloud";
import "echarts-gl";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import CameraPreview from "@/components/CameraPreview/index";

export default {
  name: "",
  components: { Treeselect, CameraPreview },
  data() {
    return {
      playShow: false,
      splitNum: 3,
      playURL: "",
      cameraWidth: "470px",
      cameraHeight: "360px",
      areaId: undefined,
      // projectId: undefined,
      // 访客统计下拉框
      year2: [
        { name: "2022" },
        { name: "2021" },
        { name: "2020" },
        { name: "2019" },
        { name: "2018" },
      ],
      year: "",
      community: [
        { name: "2021" },
        { name: "2020" },
        { name: "2019" },
        { name: "2018" },
      ],
      community1: "",
      // 访客统计
      month: "",
      month2: [
        { name: "01" },
        { name: "02" },
        { name: "03" },
        { name: "04" },
        { name: "05" },
        { name: "06" },
        { name: "07" },
        { name: "08" },
        { name: "09" },
        { name: "10" },
        { name: "11" },
        { name: "12" },
      ],
      // 社区人行/车行进出流量走势图-人行
      month3: "",
      month4: [
        { name: "01" },
        { name: "02" },
        { name: "03" },
        { name: "04" },
        { name: "05" },
        { name: "06" },
        { name: "07" },
        { name: "08" },
        { name: "09" },
        { name: "10" },
        { name: "11" },
        { name: "12" },
      ],
      // 年度维修设备统计
      month5: "",
      month6: [
        { name: "2024" },
        { name: "2023" },
        { name: "2022" },
        { name: "2021" },
        { name: "2020" },
      ],
      typeList: [],
      typeName: "",
      typeItemName: "",
      // 社区人行下拉框
      shuju3: [
        { name: "1" },
        { name: "2" },
        { name: "3" },
        { name: "4" },
        { name: "5" },
        { name: "6" },
        { name: "7" },
        { name: "8" },
        { name: "9" },
        { name: "10" },
        { name: "11" },
        { name: "12" },
      ],
      one3: "",
      // 省
      provinces: [
        // { name: "江苏省" },
        // { name: "浙江省" },
        // { name: "江西省" },
        // { name: "广东省" },
        // { name: "海南省" }
      ],
      // 市
      citys: [],
      // 区
      areas: [],
      //  选择项目
      projects: [],
      params: {
        provinces: "",
        citys: "",
        areas: "",
        projects2: "",
      },
      // 时间实时展示
      nowTime: "",
      nowTime2: "",
      nowTime3: "",
      // 7日巡查状况星期
      nowTime4: "",
      nowTime5: "",
      nowTime6: "",
      nowTime7: "",
      nowTime8: "",
      nowTime9: "",
      nowTime10: "",

      active1: require("../../assets/largeScreen/1xunchaxungeng.png"),
      active2: require("../../assets/largeScreen/2xunchaxungeng.png"),
      active3: require("../../assets/largeScreen/3xunchaxungeng.png"),
      active4: require("../../assets/largeScreen/4xunchaxungeng.png"),
      active5: require("../../assets/largeScreen/5xunchaxungeng.png"),
      active6: require("../../assets/largeScreen/6xunchaxungeng.png"),
      active7: require("../../assets/largeScreen/7xunchaxungeng.png"),
      inactive1: require("../../assets/largeScreen/xunchaxungeng1.png"),
      inactive2: require("../../assets/largeScreen/xunchaxungeng2.png"),
      inactive3: require("../../assets/largeScreen/xunchaxungeng3.png"),
      inactive4: require("../../assets/largeScreen/xunchaxungeng4.png"),
      inactive5: require("../../assets/largeScreen/xunchaxungeng5.png"),
      inactive6: require("../../assets/largeScreen/xunchaxungeng6.png"),
      inactive7: require("../../assets/largeScreen/xunchaxungeng7.png"),
      // 物业服务人员
      servicePersonnel: "",
      // 物业服务人员
      management: "",
      // 物业维修人员
      maintenance: "",
      // 物业安保人员
      security: "",
      // 小区房间房态数量统计
      checkIn: "",
      // 大屏业主年龄分布数据
      checkIn2: "",
      // 社区人员结构
      checkIn3: "",
      // 社区情况统计
      checkIn4: "",
      // 居住舒适质量
      checkIn5: "",
      checkIn6: "",
      // 业主所占比例
      ownerProportion: 0,
      // 租客所占比例
      tenantProportion: 0,
      // 按月查询每天访问次数
      count: [],
      // 按月查询每天
      dayDate: "",
      // 按月查询每天访问次数
      count2: [],
      // 按月查询每天
      dayDate2: [],
      // 小区人行/车行进出流量走势图
      count3: [],
      // 小区人行/车行进出流量走势图
      count4: [],
      // 小区人行/车行进出流量走势图
      days: [],
      // 小区人行/车行进出流量走势图
      userVehicles: [],
      // 年度维修设备统计
      maintenanceEquipmentVos: [],
      // 按年查询每月访问次数
      monthCount: [],
      // 按年查询每月
      monthDate: [],
      // 设备统计
      barrier: "",
      // 自动定位城市
      city: "",
      // 工单统计
      workOrderSum: "",
      // 工单总数
      totalNumber: "",
      // 当天每小时访问次数
      zeroToTwo: "",
      // 在线设备数
      online: "",
      // 离线设备数
      offline: "",
      // 社区人流量统计
      twoToThree: "",
      // 巡查巡更巡检列表
      patrolList: [],
      // 年度报事报修
      parkRepair: "",
      // 城建·彩金湖畔
      coverImgUrl: {
        backgroundImage:
          "url(" + require("../../assets/largeScreen/caijin.png") + ")",
      },
      // 城建·世纪阳江 shiji
      coverImgUrl2: {
        backgroundImage:
          "url(" + require("../../assets/largeScreen/caijin.png") + ")",
      },
      // 默认背景图 century
      coverImgUrl3: {
        backgroundImage:
          "url(" + require("../../assets/largeScreen/caijin.png") + ")",
      },
      // 高德地图的key值
      // num_sort:''
      // 省市区和小区数据
      areaList: [],
      nodeId: "",
      projectList: [],
      // 监控数据
      Equipment: undefined,
      personnels: [],

      player: null,
      playback: {
        rate: "",
      },
      video: "",
      sust: -1,
      list: [],
      monitors: [],
      flag: true,
      isactive: "", //默认第一个有样式
      // 小区id
      communityId: "",
      // 默认小区id
      // communityIdTwo:1,
      Ranking: "",

      Alarm: "",
      arrFive: "",

      arrSix: "",
      arrSeven: "",
      // 物业收费展示
      charges: "",

      device: [],

      // 7日巡查状况
      countPatrolOrder: "",
      duringMonthPatrolOrder: "",
      visitorsTraffic: "",
      visitorsTrafficPatrol: "",
      oneList: "",
      twoList: "",
      threeList: "",
      fourList: "",
      fiveList: "",
      sixList: "",
      sevenList: "",

      oneDate: "",
      twoDate: "",
      threeDate: "",
      fourDate: "",
      fiveDate: "",
      sixDate: "",
      sevenDate: "",

      // 访客统计
      monthVisits: "",

      // 小区实时监控显示与隐藏
      communityMonitoring: false,

      // 一键处理申请
      weeks: "",
      // 一键处理申请
      totalData: "",
      max: 0,
      maxTwo: 0,
      maxThree: 0,
      maxFour: 0,
      maxFive: 0,
      maxSix: 0,
      maxSeven: 0,
      maxEight: 0,
      maxNine: 0,
      maxTen: 0,
      hik: {},
      initCount: 0,
      pubKey: "",
      oWebControl: null,
      width: 300,
      height: 170,
      argumentList: [],
      curIndex: 1,
      bodyWidth: 0,
      bodyHeight: 0,
      wRate: 1,
      hRate: 1,
      playerShow: false,
      playerText: "暂无数据",
      selectInvest: [],
      selectNotice: [],
      optionsSmart: [
        {
          value: 1,
          label: "通知公告",
        },
        {
          value: 2,
          label: "公示公告",
        },
      ],
      valueSmart: 1,
      typeItemList: [],
      EnvironmentalTesting: [],
    };
  },
  props: {},
  created() {
    let date = new Date();
    let datas = [];
    let year = date.getFullYear();
    let month = date.getMonth();
    let num = year - 2024;
    for (let i = 0; i < num + 1; i++) {
      let obj = {};
      obj.name = year - i;
      datas.push(obj);
    }
    if (month == 0) {
      datas = datas.slice(1);
    }
    datas[0].check = true;
    this.month6 = datas;
    this.year2 = datas;
    function placeholderPic() {
      var w = document.documentElement.offsetWidth;
      document.documentElement.style.fontSize = w / 420 + "px";
      //document.documentElement.style.fontSize = w / 150 + "px";
    }
    placeholderPic();
    window.onresize = function () {
      placeholderPic();
    };
    // 获取部门下拉树列表
    this.areaTreeSelect();
    // 实时显示时间;
    this.nowTimes();
    // 实时显示时间;
    this.newMonth(new Date());
    // this.initPageData();
    this.getdictData();
  },
  mounted() {
    let monitoringTop = this.$refs.monitoringTop;
    this.cameraWidth = monitoringTop.clientWidth + "px";
    this.cameraHeight = monitoringTop.clientHeight + "px";
    // 智慧电能
    // this.electricEnergy();
    // 高德定位
    this.location();
  },
  destroyed() {
    this.stopAllPlay();
  },
  computed: {
    styleVar() {
      return this.communityMonitoring == false
        ? "justify-content: flex-end"
        : "justify-content: space-between";
    },
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
    initPageData() {
      // 物业服务人员
      this.initProvice();
      // 小区房间房态数量统计
      this.getRoomStatus();
      // 社区情况统计
      this.getCommunitySituation();
      // 居住舒适质量
      this.getCommunitycomfort();
      // 社区人员结构
      this.getCommunitys();
      // 大屏业主年龄分布数据
      this.getDistribution();
      // 设备统计
      this.getEquipment();
      // 设备在线状态统计
      this.getOnlineStatus();
      // 巡查巡更巡检列表
      this.getPatrolLists();
      // 工单统计
      this.getWorkOrder();
      // 小区设备告警统计
      this.equipmentAlarm();
      // 一键处理申请
      this.getProcessing();
      // 一键处理申请
      this.getTwoProcessing();
      // 物业排行
      this.propertyRanking();
      // 社区人流量统计
      this.getCommunityTraffic();
      // 年度报事报修
      this.getReportRepair();
      // 7日巡查状况
      this.patrolStatus();
      // 物业收费展示
      this.feeDisplay();
      // 实时入园人员展示
      this.admissionPersonnel();
      // 访客统计按月查询每天数据
      this.getQueryDaily();
      // 社区人行/车行进出流量走势图-人行
      this.inOutHuman();
      // 访客统计按年查询每月访问次数
      this.getQueryYear();
      // 年度维修设备统计
      this.annualMaintenance();
      // 设备异常列表
      this.exceptionList();
      // 小区实时监控
      this.getEquipmentInformation();
      // 招商公示
      this.getselectInvest();
      // 通知公告
      this.getselectNotice();
      // 水电表
      this.selectWaterNum();
    },

    // 招商公示
    getselectInvest() {
      getselectInvest({ communityId: 1 }).then((res) => {
        this.selectInvest = res.data;
      });
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
    // 通知公告
    getselectNotice() {
      getselectNotice({ communityId: this.communityId }).then((res) => {
        this.selectNotice = res.data;
      });
    },

    chooseSmart() {
      if (this.valueSmart == 1) {
        this.getselectNotice();
      } else {
        this.getselectInvest();
      }
    },

    // 通过小区id查询数据
    projectData(event) {
      //  let communityId = event.target.value
      this.communityId = event.target.value;
      this.communityMonitoring = true;
      this.initPageData();
    },

    monitorPage(e, any) {
      let isExist = e.target.visibilityState;
      if (isExist === "visible") {
        console.log(new Date().toLocaleString(), `您已进入页面！`);
      } else {
        this.stopAllPlay();
        console.log(new Date().toLocaleString(), `您已离开页面！`);
      }
    },
    //监控点击播放
    clickMonitoring(camrea, index) {
      this.isactive = index;
      if (this.curIndex >= 9) {
        this.curIndex = 1;
      } else {
        this.curIndex++;
      }
      getCameraById(camrea).then((res) => {
        this.$refs.camera.preview(this.curIndex, res.url);
      });
    },

    //获取监控设备
    getEquipmentInformation() {
      console.log("监控设备");
      /*this.stopAllPlay();
      this.playerShow = false;
      this.playerText = "视频监控加载中。。。";*/
      if (this.communityId != "") {
        getEquipmentInformation({ communityId: this.communityId }).then(
          (res) => {
            this.monitors = res.data;
            /*if(res.data.length > 0) {
            this.cameraPlay(res.data);
          }else {
            this.playerText = "暂无数据";
          }*/
          }
        );
      }
    },

    admissionPersonnel() {
      // 实时入园人员展示
      admissionPersonnel({ communityId: this.communityId }).then((res) => {
        this.personnels = res.data;
      });
    },

    // 查询项目数据
    getProject() {
      if (this.areaId == 3) {
        getProject({ areaId: 3 }).then((res) => {
          this.projects = res.data;
          this.communityId = res.data[0].communityId;
          this.params.projects2 = res.data[0].communityId;
          this.communityMonitoring = true;
          this.initPageData();
        });
      } else {
        getProject({ areaId: this.nodeId }).then((res) => {
          this.projects = res.data;
          this.communityId = res.data[0].communityId;
          this.params.projects2 = res.data[0].communityId;
          this.communityMonitoring = true;
          this.initPageData();
        });
      }
    },

    // 获取省市区下拉树列表
    areaTreeSelect() {
      console.log("神市区");
      areaTreeSelect().then((res) => {
        this.areaList = res.data;
        try {
          this.areaList.forEach((item, index) => {
            if (item.children.length) {
              item.children.forEach((items, inx) => {
                console.log(items);
                if (item.children.length) {
                  items.children.forEach((itemss, inx) => {
                    console.log(itemss);
                    if (itemss.type > 0) {
                      this.nodeId = itemss.id;
                      this.areaId = itemss.id;
                      throw new Error(itemss.label);
                    }
                  });
                } else {
                  if (items.type > 0) {
                    this.nodeId = items.id;
                    this.areaId = items.id;
                    throw new Error(items.label);
                  }
                }
              });
            } else {
              if (item.type > 0) {
                this.nodeId = item.id;
                this.areaId = item.id;
                throw new Error(item.label);
              }
            }
          });
        } catch (e) {
          console.log(e);
          this.getProject();
        }
      });
    },

    // 物业排行
    propertyRanking() {
      propertyRanking({
        communityId: this.communityId,
      }).then((res) => {
        this.Ranking = res.data;
      });
    },

    // 小区设备告警统计
    equipmentAlarm() {
      equipmentAlarm().then((res) => {
        this.Alarm = res.data;
        this.arrFive = [
          res.data[0].equipmentAlarmNum,
          res.data[1].equipmentAlarmNum,
          res.data[2].equipmentAlarmNum,
          res.data[3].equipmentAlarmNum,
        ];
        // 小区设备告警统计
        this.allocationDelay();
      });
    },

    // 7日巡查状况
    patrolStatus() {
      patrolStatus({ communityId: this.communityId }).then((res) => {
        this.countPatrolOrder = res.countPatrolOrder;
        this.duringMonthPatrolOrder = res.duringMonthPatrolOrder;
        this.visitorsTraffic = res.visitorsTraffic;
        this.visitorsTrafficPatrol = res.visitorsTrafficPatrol;
        this.oneList = res.list[0];
        this.twoList = res.list[1];
        this.threeList = res.list[2];
        this.fourList = res.list[3];
        this.fiveList = res.list[4];
        this.sixList = res.list[5];
        this.sevenList = res.list[6];
        if (this.nowTime2 == "星期二") {
          let weekByDate = ["二", "一", "日", "六", "五", "四", "三"];
          this.oneDate = weekByDate[0];
          this.twoDate = weekByDate[1];
          this.threeDate = weekByDate[2];
          this.fourDate = weekByDate[3];
          this.fiveDate = weekByDate[4];
          this.sixDate = weekByDate[5];
          this.sevenDate = weekByDate[6];
        } else if (this.nowTime2 == "星期三") {
          let weekByDate = ["三", "二", "一", "日", "六", "五", "四"];
          this.oneDate = weekByDate[0];
          this.twoDate = weekByDate[1];
          this.threeDate = weekByDate[2];
          this.fourDate = weekByDate[3];
          this.fiveDate = weekByDate[4];
          this.sixDate = weekByDate[5];
          this.sevenDate = weekByDate[6];
        } else if (this.nowTime2 == "星期四") {
          let weekByDate = ["四", "三", "二", "一", "日", "六", "五"];
          this.oneDate = weekByDate[0];
          this.twoDate = weekByDate[1];
          this.threeDate = weekByDate[2];
          this.fourDate = weekByDate[3];
          this.fiveDate = weekByDate[4];
          this.sixDate = weekByDate[5];
          this.sevenDate = weekByDate[6];
        } else if (this.nowTime2 == "星期五") {
          let weekByDate = ["五", "四", "三", "二", "一", "日", "六"];
          this.oneDate = weekByDate[0];
          this.twoDate = weekByDate[1];
          this.threeDate = weekByDate[2];
          this.fourDate = weekByDate[3];
          this.fiveDate = weekByDate[4];
          this.sixDate = weekByDate[5];
          this.sevenDate = weekByDate[6];
        } else if (this.nowTime2 == "星期六") {
          let weekByDate = ["六", "五", "四", "三", "二", "一", "日"];
          this.oneDate = weekByDate[0];
          this.twoDate = weekByDate[1];
          this.threeDate = weekByDate[2];
          this.fourDate = weekByDate[3];
          this.fiveDate = weekByDate[4];
          this.sixDate = weekByDate[5];
          this.sevenDate = weekByDate[6];
        } else if (this.nowTime2 == "星期日") {
          let weekByDate = ["日", "六", "五", "四", "三", "二", "一"];
          this.oneDate = weekByDate[0];
          this.twoDate = weekByDate[1];
          this.threeDate = weekByDate[2];
          this.fourDate = weekByDate[3];
          this.fiveDate = weekByDate[4];
          this.sixDate = weekByDate[5];
          this.sevenDate = weekByDate[6];
        } else if (this.nowTime2 == "星期一") {
          let weekByDate = ["一", "日", "六", "五", "四", "三", "二"];
          this.oneDate = weekByDate[0];
          this.twoDate = weekByDate[1];
          this.threeDate = weekByDate[2];
          this.fourDate = weekByDate[3];
          this.fiveDate = weekByDate[4];
          this.sixDate = weekByDate[5];
          this.sevenDate = weekByDate[6];
        }
        // this.oneDate=res.weekByDate[0]
        // this.twoDate=res.weekByDate[1]
        // this.threeDate=res.weekByDate[2]
        // this.fourDate=res.weekByDate[3]
        // this.fiveDate=res.weekByDate[4]
        // this.sixDate=res.weekByDate[5]
        // this.sevenDate=res.weekByDate[6]
        // console.log(res, "7日巡查状况");
      });
    },

    // 物业收费展示
    feeDisplay() {
      feeDisplay({ communityId: this.communityId }).then((res) => {
        this.charges = res.data;
      });
    },

    // 设备异常列表
    exceptionList() {
      exceptionList({ communityId: this.communityId }).then((res) => {
        this.device = res.data;
      });
    },

    selectArea(node) {
      this.nodeId = node.id;
      this.getProject();
    },

    // 高德定位
    location() {
      this.$axios
        .get(
          "https://restapi.amap.com/v3/ip?key=c6a5cfa927ab63cc03f685b38acea922"
        )
        .then((res) => {
          this.city = res.data.city;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    // 物业服务人员
    initProvice() {
      // let deptId= localStorage.getItem('deptId')
      getService({ communityId: this.communityId }).then((res) => {
        this.servicePersonnel = res.data;
        // this.management = '0'
        this.management = (parseFloat(res.data.afryzb) * 100).toFixed(1);
        this.maintenance = (parseFloat(res.data.wxryzb) * 100).toFixed(1);
        this.security = (parseFloat(res.data.wyglryzb) * 100).toFixed(1);
        // 物业服务人员进度条
        this.progress();
      });
    },
    // 小区房间房态数量统计
    getRoomStatus() {
      getRoomStatus({ communityId: this.communityId }).then((res) => {
        this.checkIn = [
          res.data.sellingNum,
          res.data.checkInNum,
          res.data.rentNum,
          res.data.furnishNum,
        ];
        // 小区横向柱状图
        this.histogram();
      });
    },
    // 大屏业主年龄分布数据
    getDistribution() {
      getDistribution().then((res) => {
        let oneToNine = res.data;
        this.checkIn2 = [
          res.data.ten,
          res.data.twenty,
          res.data.forty,
          res.data.overForty,
        ];
        // 业主横向柱状图
        this.transverse(res.data.total);
        // 3D饼状图
        let tenRate = "0";
        let twentyRate = "0";
        let fortyRate = "0";
        let overFortyRate = "0";
        if (res.data.total > 0) {
          if (res.data.ten > 0) {
            tenRate = ((res.data.ten * 100) / res.data.total).toFixed(1);
          }
          if (res.data.twenty > 0) {
            twentyRate = ((res.data.twenty * 100) / res.data.total).toFixed(1);
          }
          if (res.data.forty > 0) {
            fortyRate = ((res.data.forty * 100) / res.data.total).toFixed(1);
          }
          if (res.data.overForty > 0) {
            overFortyRate = (
              (res.data.overForty * 100) /
              res.data.total
            ).toFixed(1);
          }
        }
        this.pieChart(tenRate, twentyRate, fortyRate, overFortyRate);
      });
    },
    // 小区情况统计
    getCommunitySituation() {
      console.log(this.communityId, "this.communityId11111");
      getCommunitySituation({ communityId: this.communityId }).then((res) => {
        this.checkIn4 = res.data;
      });
    },
    // 居住舒适质量
    getCommunitycomfort() {
      getCommunitycomfort({ communityId: this.communityId }).then((res) => {
        this.checkIn5 = res.data;
        this.checkIn6 = [res.data.plotRatio, res.data.greenRatio];
        this.quarterEcharts();
      });
    },
    // 社区人员结构
    getCommunitys() {
      getCommunitys({ communityId: this.communityId }).then((res) => {
        this.checkIn3 = res.data;
        let total = res.data.ownerTotal + res.data.tenantTotal;
        if (total > 0) {
          this.ownerProportion = ((res.data.ownerTotal * 100) / total).toFixed(
            1
          );
          this.tenantProportion = (
            (res.data.tenantTotal * 100) /
            total
          ).toFixed(1);
        }
        // 饼图引导线
        this.getA();
      });
    },

    // 访客统计按月查询每天数据
    getQueryDaily() {
      getQueryDaily({
        month: parseFloat(this.month),
        communityId: this.communityId,
      }).then((res) => {
        let dayData = res.dayData;
        let visitList = res.visitList;
        // 访客统计折线图
        this.discount(dayData, visitList);
      });
    },

    // 访客统计按年查询每月访问次数
    getQueryYear() {
      getQueryYear({
        year: parseFloat(this.year),
        communityId: this.communityId,
      }).then((res) => {
        let dayData = res.dayData;
        let visitList = res.visitList;
        // 立体渐变柱状图
        this.gradient(dayData, visitList);
      });
    },

    // 设备统计
    getEquipment() {
      getEquipment({ communityId: this.communityId }).then((res) => {
        this.barrier = res.data;
        // 各设备统计
        this.statisticsOfEachEquipment();
      });
    },

    // 一键处理申请
    getProcessing() {
      getProcessing().then((res) => {
        this.weeks = [
          res.data.monday,
          res.data.tuesday,
          res.data.wednesday,
          res.data.thursday,
          res.data.friday,
          res.data.saturday,
          res.data.sunday,
          res.data.countNumber,
        ];
        // 一键处理申请
        this.statisticsApply();
      });
    },

    // 一键处理申请
    getTwoProcessing() {
      getTwoProcessing().then((res) => {
        this.totalData = res.data;
      });
    },

    // 工单统计
    getWorkOrder() {
      getWorkOrder().then((res) => {
        let patrolCount = res.data;
        let arr3 = [
          patrolCount.reportCount,
          patrolCount.decorateCount,
          patrolCount.cleanCount,
          patrolCount.inspectionCount,
          patrolCount.watchmanCount,
          patrolCount.patrolCount,
          patrolCount.meterCount,
        ];
        this.totalNumber = patrolCount.workOrderSum;
        this.workOrderSum = arr3;
        // 工单统计
        this.statisticsWorkOrder();
      });
    },
    // 设备在线状态统计
    getOnlineStatus() {
      getOnlineStatus({ communityId: this.communityId }).then((res) => {
        this.online = res.data.online;
        this.offline = res.data.offline;
        // 设备状态统计
        this.stateStatistics();
      });
    },
    // 社区人流量统计
    getCommunityTraffic() {
      getCommunityTraffic({ communityId: this.communityId }).then((res) => {
        let dayData = res.dayData;
        let userData = res.userData;
        let vehicleInData = res.vehicleInData;
        let vehicleOutData = res.vehicleOutData;
        // 社区人流量统计
        this.flowSingle(dayData, userData);
        this.flowDouble(dayData, vehicleInData, vehicleOutData);
      });
    },
    // 巡查巡更巡检列表
    getPatrolLists() {
      getPatrolLists({ communityId: this.communityId }).then((res) => {
        this.patrolList = res.data.slice(0, 50);
      });
    },
    // 年度报事报修
    getReportRepair() {
      getReportRepair({ communityId: this.communityId }).then((res) => {
        this.parkRepair = res.data;
        // 年度报事报修
        this.repairReportOne();
      });
    },

    // 社区人行/车行进出流量走势图-人行
    inOutHuman() {
      inOutHuman({
        month: parseFloat(this.month3),
        communityId: this.communityId,
      }).then((res) => {
        if (res.vehicleList != null) {
          this.count3 = [];
          this.count4 = [];
          this.maxSix = 0;
          this.maxSeven = 0;
          this.days = res.dayData;
          this.userVehicles = res.vehicleList;
          res.userList.forEach((item, index) => {
            this.count3.push(item);
            if (item > this.maxSix) {
              this.maxSix = item;
            }
          });
          res.vehicleList.forEach((item, index) => {
            this.count4.push(item);
            if (item > this.maxSeven) {
              this.maxSeven = item;
            }
          });
          this.polyline();
        } else {
          this.userVehicles = res.vehicleList;
          this.count3 = res.userList;
          this.count4 = res.vehicleList;
          this.polyline();
        }
      });
    },

    // 年度维修设备统计
    getMaintenance() {
      // 年度维修设备统计
      this.annualMaintenance();
    },

    // 年度维修设备统计
    annualMaintenance() {
      annualMaintenance({
        year: parseFloat(this.month5),
        communityId: this.communityId,
      }).then((res) => {
        if (res.data.maintenanceEquipmentVos[0].dateMonth != null) {
          this.maintenanceEquipmentVos = [];
          this.maxEight = 0;
          res.data.maintenanceEquipmentVos.forEach((item, index) => {
            this.maintenanceEquipmentVos.push(item.num);
            if (item.num > this.maxEight) {
              this.maxEight = item.num;
            }
          });
          this.maintenanceAnnual();
        } else {
          this.maintenanceEquipmentVos = [];
          this.maintenanceAnnual();
        }
      });
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
    //显示当前时间（年月日时分秒）
    timeFormate(timeStamp) {
      let year = new Date(timeStamp).getFullYear();
      let month =
        new Date(timeStamp).getMonth() + 1 < 10
          ? "0" + (new Date(timeStamp).getMonth() + 1)
          : new Date(timeStamp).getMonth() + 1;
      let date =
        new Date(timeStamp).getDate() < 10
          ? "0" + new Date(timeStamp).getDate()
          : new Date(timeStamp).getDate();
      let hh =
        new Date(timeStamp).getHours() < 10
          ? "0" + new Date(timeStamp).getHours()
          : new Date(timeStamp).getHours();
      let mm =
        new Date(timeStamp).getMinutes() < 10
          ? "0" + new Date(timeStamp).getMinutes()
          : new Date(timeStamp).getMinutes();
      let ss =
        new Date(timeStamp).getSeconds() < 10
          ? "0" + new Date(timeStamp).getSeconds()
          : new Date(timeStamp).getSeconds();
      let week = new Date(timeStamp).getDay();
      let weeks = ["日", "一", "二", "三", "四", "五", "六"];
      let getWeek = "星期" + weeks[week];
      let getWeek2 = weeks[week - 3];
      let getWeek3 = weeks[week - 2];
      let getWeek4 = weeks[week - 1];
      let getWeek5 = weeks[week];
      let getWeek6 = weeks[week + 1];
      let getWeek7 = weeks[week + 2];
      let getWeek8 = weeks[0];
      this.nowTime = hh + ":" + mm + ":" + ss;
      this.nowTime2 = getWeek;
      // 7日巡查状况星期
      this.nowTime4 = getWeek2;
      this.nowTime5 = getWeek3;
      this.nowTime6 = getWeek4;
      this.nowTime7 = getWeek5;
      this.nowTime8 = getWeek6;
      this.nowTime9 = getWeek7;
      this.nowTime10 = getWeek8;
      this.nowTime3 = year + "." + month + "." + date;
    },
    //显示当前时间（年月日时分秒）
    nowTimes() {
      this.timeFormate(new Date());
      setInterval(this.nowTimes, 1000);
      this.clear();
    },
    //显示当前时间（年月日时分秒）
    clear() {
      clearInterval(this.nowTimes);
      this.nowTimes = null;
    },

    // 获取当前月份
    newMonth(timeStamp) {
      let year = new Date(timeStamp).getFullYear();
      let month =
        new Date(timeStamp).getMonth() + 1 < 10
          ? "0" + (new Date(timeStamp).getMonth() + 1)
          : new Date(timeStamp).getMonth() + 1;
      this.month = month;
      this.month3 = month;
      this.month5 = year;
      this.year = year;
      // 访客统计按月查询每天数据
      this.getQueryDaily();
      // 访客统计按年查询每月数据
      this.getQueryYear();
      // 社区人行/车行进出流量走势图-人行
      this.inOutHuman();
      // 年度维修设备统计
      this.annualMaintenance();
    },

    // 饼图引导线
    getA() {
      let that = this;
      let myChart = echarts.init(this.$refs.option);
      let index = 0;
      var colorList = ["#48a0f8", "#42f1f3"];
      function fun() {
        var timer = setInterval(function () {
          myChart.dispatchAction({
            type: "hideTip",
            seriesIndex: 0,
            dataIndex: index,
          });
          // 显示提示框
          myChart.dispatchAction({
            type: "showTip",
            seriesIndex: 0,
            dataIndex: index,
          });
          // 取消高亮指定的数据图形
          myChart.dispatchAction({
            type: "downplay",
            seriesIndex: 0,
            dataIndex: index == 0 ? 5 : index - 1,
          });
          myChart.dispatchAction({
            type: "highlight",
            seriesIndex: 0,
            dataIndex: index,
          });
          index++;
          if (index > 5) {
            index = 0;
          }
        }, 3000);
      }

      fun();
      setTimeout(function () {
        fun();
      }, 5000);
      let option = {
        title: {
          text: "",
          x: "center",
          y: "center",
          textStyle: {
            fontSize: 20,
          },
        },
        tooltip: {
          confine: true,
          trigger: "item",
        },
        series: [
          {
            type: "pie",
            center: ["50%", "58%"],
            radius: ["24%", "45%"],
            clockwise: true,
            avoidLabelOverlap: true,
            hoverOffset: 15,
            itemStyle: {
              normal: {
                color: function (params) {
                  return colorList[params.dataIndex];
                },
              },
            },
            label: {
              show: true,
              position: "outside",
              formatter: "{a|{b}：\n{d}%}\n{hr|}",
              rich: {
                hr: {
                  // backgroundColor: 't',
                  // borderRadius: 3,
                  // width: 3,
                  // height: 1,
                  // padding: [3, 3, 0, -12]
                },
                a: {
                  padding: [-30, 1, -20, 1],
                  lineHeight: 16,
                },
              },
            },
            labelLine: {
              normal: {
                length: 10,
                length2: 16,
                lineStyle: {
                  width: 2,
                },
              },
            },
            data: [
              { name: "租户", value: that.tenantProportion },
              { name: "业户", value: that.ownerProportion },
            ],
          },
        ],
      };

      myChart.setOption(option);
    },
    // 间隔柱状图   居住舒适质量
    quarterEcharts() {
      let myChart = echarts.init(this.$refs.option9);
      let yData = this.checkIn6;
      let bgData = [];
      for (let i in yData) {
        bgData.push(100);
      }
      let option9 = {
        xAxis: {
          splitLine: {
            show: false,
          },
          axisLine: {
            show: false,
          },
          axisLabel: {
            show: false,
          },
          axisTick: {
            show: false,
          },
        },
        grid: {
          left: 180,
          top: 20, // 设置条形图的边距
          right: 180,
          bottom: 20,
        },
        yAxis: [
          {
            type: "category",
            show: true,
            inverse: false,
            data: ["容积率（%）", "绿化率（%）"],
            axisLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            axisLabel: {
              // 坐标轴刻度标签的相关设置
              show: true,
              margin: 15,
              textStyle: {
                fontSize: 18,
                color: "#fff",
                // color: function (value) {
                //   return value == "容积率（%）" ? "#20A6FF" : "#00F9EE";
                // },
              },
              interval: 0,
            },
            inverse: true,
          },
          {
            type: "category",
            inverse: true,
            axisTick: "none",
            axisLine: "none",
            show: true,
            axisLabel: {
              textStyle: {
                color: "#fff",
                fontSize: "28",
              },
            },
            data: yData,
          },
        ],
        series: [
          {
            // 内
            type: "bar",
            barWidth: 10,
            legendHoverLink: false,
            silent: true,
            itemStyle: {
              normal: {
                barBorderRadius: [6, 6, 6, 6],
                color: function (params) {
                  var colorlist = ["#20A6FF", "#00F9EE"];
                  return colorlist[params.dataIndex];
                },
              },
            },
            shadowBlur: 0,
            culling: true, // 开启裁剪
            data: yData,
            z: 2,
            animationEasing: "elasticOut",
          },
          {
            // 分隔
            type: "pictorialBar",
            itemStyle: {
              normal: {
                color: function (params) {
                  var colorlist = ["#023779", "#006972"];
                  return colorlist[params.dataIndex];
                },
              },
            },
            symbolRepeat: "fixed",
            symbolMargin: 2, //图形的两边间隔
            symbol: "rect",
            symbolClip: true, //是否裁剪图形
            symbolSize: [3, 10],
            symbolPosition: "start", //图形的定位位置。
            symbolOffset: [-3, 0], //图形相对于原本位置的偏移。
            data: yData,
            z: 3,
            animationEasing: "elasticOut",
          },
          {
            name: "背景",
            type: "bar",
            barWidth: 10,
            barGap: "-100%",
            data: bgData,
            itemStyle: {
              normal: {
                color: function (params) {
                  var colorlist = ["#023779", "#006972"];
                  return colorlist[params.dataIndex];
                },
                barBorderRadius: [6, 6, 6, 6],
                shadowBlur: 0,
              },
            },
            culling: true, // 开启裁剪
            z: 1,
          },
        ],
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option9);
      //若盒子宽度设为百分比，这里做自适应
      window.addEventListener("resize", () => {
        if (myChart) {
          myChart.resize();
        }
      });
    },
    // 3D饼状图
    pieChart(tenRate, twentyRate, fortyRate, overFortyRate) {
      let myChart = echarts.init(this.$refs.option2);
      // 生成扇形的曲面参数方程，用于 series-surface.parametricEquation
      function getParametricEquation(
        startRatio,
        endRatio,
        isSelected,
        isHovered,
        k
      ) {
        // 计算
        let midRatio = (startRatio + endRatio) / 2;

        let startRadian = startRatio * Math.PI * 2;
        let endRadian = endRatio * Math.PI * 2;
        let midRadian = midRatio * Math.PI * 2;

        // 如果只有一个扇形，则不实现选中效果。
        if (startRatio === 0 && endRatio === 1) {
          isSelected = false;
        }

        // 通过扇形内径/外径的值，换算出辅助参数 k（默认值 1/3）
        k = typeof k !== "undefined" ? k : 1 / 3;

        // 计算选中效果分别在 x 轴、y 轴方向上的位移（未选中，则位移均为 0）
        let offsetX = isSelected ? Math.cos(midRadian) * 0.1 : 0;
        let offsetY = isSelected ? Math.sin(midRadian) * 0.1 : 0;

        // 计算高亮效果的放大比例（未高亮，则比例为 1）
        let hoverRate = isHovered ? 1.05 : 1;

        // 返回曲面参数方程
        return {
          u: {
            min: -Math.PI,
            max: Math.PI * 3,
            step: Math.PI / 32,
          },

          v: {
            min: 0,
            max: Math.PI * 2,
            step: Math.PI / 20,
          },

          x: function (u, v) {
            if (u < startRadian) {
              return (
                offsetX +
                Math.cos(startRadian) * (1 + Math.cos(v) * k) * hoverRate
              );
            }
            if (u > endRadian) {
              return (
                offsetX +
                Math.cos(endRadian) * (1 + Math.cos(v) * k) * hoverRate
              );
            }
            return offsetX + Math.cos(u) * (1 + Math.cos(v) * k) * hoverRate;
          },

          y: function (u, v) {
            if (u < startRadian) {
              return (
                offsetY +
                Math.sin(startRadian) * (1 + Math.cos(v) * k) * hoverRate
              );
            }
            if (u > endRadian) {
              return (
                offsetY +
                Math.sin(endRadian) * (1 + Math.cos(v) * k) * hoverRate
              );
            }
            return offsetY + Math.sin(u) * (1 + Math.cos(v) * k) * hoverRate;
          },

          z: function (u, v) {
            if (u < -Math.PI * 0.5) {
              return Math.sin(u);
            }
            if (u > Math.PI * 2.5) {
              return Math.sin(u);
            }
            return Math.sin(v) > 0 ? 1 : -1;
          },
        };
      }

      // 生成模拟 3D 饼图的配置项
      function getPie3D(pieData, internalDiameterRatio) {
        let series = [];
        let sumValue = 0;
        let startValue = 0;
        let endValue = 0;
        let legendData = [];
        let k =
          typeof internalDiameterRatio !== "undefined"
            ? (1 - internalDiameterRatio) / (1 + internalDiameterRatio)
            : 1 / 3;

        // 为每一个饼图数据，生成一个 series-surface 配置
        for (let i = 0; i < pieData.length; i++) {
          sumValue += pieData[i].value;

          let seriesItem = {
            name:
              typeof pieData[i].name === "undefined"
                ? `series${i}`
                : pieData[i].name,
            type: "surface",
            parametric: true,
            wireframe: {
              show: false,
            },
            pieData: pieData[i],
            pieStatus: {
              selected: false,
              hovered: false,
              k: k,
            },
          };

          if (typeof pieData[i].itemStyle != "undefined") {
            let itemStyle = {};

            typeof pieData[i].itemStyle.color != "undefined"
              ? (itemStyle.color = pieData[i].itemStyle.color)
              : null;
            typeof pieData[i].itemStyle.opacity != "undefined"
              ? (itemStyle.opacity = pieData[i].itemStyle.opacity)
              : null;

            seriesItem.itemStyle = itemStyle;
          }
          series.push(seriesItem);
        }

        // 使用上一次遍历时，计算出的数据和 sumValue，调用 getParametricEquation 函数，
        // 向每个 series-surface 传入不同的参数方程 series-surface.parametricEquation，也就是实现每一个扇形。
        for (let i = 0; i < series.length; i++) {
          endValue = startValue + series[i].pieData.value;

          series[i].pieData.startRatio = startValue / sumValue;
          series[i].pieData.endRatio = endValue / sumValue;
          series[i].parametricEquation = getParametricEquation(
            series[i].pieData.startRatio,
            series[i].pieData.endRatio,
            false,
            false,
            k
          );

          startValue = endValue;

          legendData.push(series[i].name);
        }

        // 补充一个透明的圆环，用于支撑高亮功能的近似实现。
        series.push({
          name: "mouseoutSeries",
          type: "surface",
          parametric: true,
          wireframe: {
            show: false,
          },
          itemStyle: {
            opacity: 0,
          },
          parametricEquation: {
            u: {
              min: 0,
              max: Math.PI * 2,
              step: Math.PI / 20,
            },
            v: {
              min: 0,
              max: Math.PI,
              step: Math.PI / 20,
            },
            x: function (u, v) {
              return Math.sin(v) * Math.sin(u) + Math.sin(u);
            },
            y: function (u, v) {
              return Math.sin(v) * Math.cos(u) + Math.cos(u);
            },
            z: function (u, v) {
              return Math.cos(v) > 0 ? 0.1 : -0.1;
            },
          },
        });

        // 准备待返回的配置项，把准备好的 legendData、series 传入。
        let option2 = {
          //animation: false,
          tooltip: {
            confine: true,
            formatter: (params) => {
              return `<span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:${params.color};"></span>
                        0~10岁：${tenRate}%<br/>
                      <span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:${params.color};"></span>
                        11~20岁：${twentyRate}%<br/>
                      <span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:${params.color};"></span>
                        21~40岁：${fortyRate}%<br/>
                      <span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:${params.color};"></span>
                        41岁以上：${overFortyRate}%<br/>`;
            },
          },
          title: {
            text: "业主各年龄段人数占比",
            x: "center",
            // left: "1%",
            top: "114",
            textStyle: {
              color: "#4BA5FF",
              fontSize: 14,
              fontWeight: 300,
            },
          },
          backgroundColor: "",
          xAxis3D: {
            min: -1,
            max: 1,
          },
          yAxis3D: {
            min: -1,
            max: 1,
          },
          zAxis3D: {
            min: -0.6,
            max: 1,
          },
          grid3D: {
            show: false,
            boxHeight: 30,
            top: "-8%",
            // left: "-18%",
            // bottom: '20%',
            viewControl: {
              //3d效果可以放大、旋转等，请自己去查看官方配置
              alpha: 34,
              beta: -50,
            },
          },
          series: series,
        };
        return option2;
      }

      // 传入数据生成 option2
      let option2 = getPie3D([
        {
          name: "0-10岁",
          value: tenRate,
          itemStyle: {
            color: "#0140c7",
            opacity: 0.8,
          },
        },
        {
          name: "11-20岁",
          value: twentyRate,
          itemStyle: {
            color: "#0172ca",
            opacity: 0.5,
          },
        },

        {
          name: "21-40岁",
          value: fortyRate,
          itemStyle: {
            color: "#138AF5",
            opacity: 0.8,
          },
        },
        {
          name: "41岁以上",
          value: overFortyRate,
          itemStyle: {
            color: "#2DD7FE",
            opacity: 0.5,
          },
        },
      ]);

      myChart.setOption(option2);
    },
    // 业主横向柱状图
    transverse(total) {
      let myChart = echarts.init(this.$refs.option3);

      let yLabel = ["0~10岁", "11~20岁", "21~40岁", "41岁以上"];
      let yData = this.checkIn2;
      let bgData = [];
      for (let i in yData) {
        bgData.push(total);
      }
      let option3 = {
        grid: {
          left: "5%",
          right: "5%",
          bottom: "5%",
          top: "10%",
          containLabel: true,
        },
        tooltip: {
          trigger: "axis",
          confine: true,
          axisPointer: {
            type: "none",
          },
          formatter: function (params) {
            return (
              params[0].name +
              "<br/>" +
              "<span style='display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:rgba(36,207,233,0.9)'></span>" +
              params[0].seriesName +
              " : " +
              params[0].value +
              " <br/>"
            );
          },
        },
        xAxis: {
          show: false,
          type: "value",
        },
        yAxis: [
          {
            type: "category",
            inverse: true,
            axisLabel: {
              show: true,
              margin: 15,
              textStyle: {
                color: "rgba(91,233,255,1)",
              },
            },
            splitLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            axisLine: {
              show: false,
            },
            data: yLabel,
          },
          {
            type: "category",
            inverse: true,
            axisTick: "none",
            axisLine: "none",
            show: true,
            axisLabel: {
              textStyle: {
                color: "#5BE9FF",
                fontSize: "12",
              },
            },
            data: yData,
          },
        ],
        series: [
          {
            name: "人数",
            type: "bar",
            zlevel: 1,
            itemStyle: {
              normal: {
                barBorderRadius: [30, 30, 30, 30],
                color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                  {
                    offset: 0,
                    color: "#0049E5",
                  },
                  {
                    offset: 1,
                    color: "#00F6EC",
                  },
                ]),
                shadowBlur: 0,
                shadowColor: "rgba(87,220,222,0.7)",
              },
            },
            barWidth: 8,
            data: yData,
          },
          {
            name: "背景",
            type: "bar",
            barWidth: 8,
            barGap: "-100%",
            data: bgData,
            itemStyle: {
              normal: {
                color: "#041028",
                borderColor: "rgba(0,246,236,0.5)",
                barBorderRadius: [30, 30, 30, 30],
                shadowBlur: 3,
              },
            },
          },
        ],
      };
      myChart.setOption(option3);
    },
    // 小区横向柱状图
    histogram() {
      let myChart = echarts.init(this.$refs.option4);

      let yLabel = ["在售", "入住", "出租", "装修"];
      let yData = [560, 324, 245, 120, 20];

      yData = this.checkIn;
      let bgData = [];
      for (let i in yData) {
        bgData.push(660);
      }
      let option4 = {
        grid: {
          left: "5%",
          right: "5%",
          bottom: "5%",
          top: "10%",
          containLabel: true,
        },
        tooltip: {
          trigger: "axis",
          confine: true,
          axisPointer: {
            type: "none",
          },
          formatter: function (params) {
            return (
              params[0].name +
              "<br/>" +
              "<span style='display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:rgba(36,207,233,0.9)'></span>" +
              params[0].seriesName +
              " : " +
              params[0].value +
              " <br/>"
            );
          },
        },
        // backgroundColor: "rgb(20,28,52)",
        xAxis: {
          show: false,
          type: "value",
        },
        yAxis: [
          {
            type: "category",
            inverse: true,
            axisLabel: {
              show: true,
              margin: 15,
              textStyle: {
                color: "rgba(91,233,255,1)",
              },
            },
            splitLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            axisLine: {
              show: false,
            },
            data: yLabel,
          },
          {
            type: "category",
            inverse: true,
            axisTick: "none",
            axisLine: "none",
            show: true,
            axisLabel: {
              textStyle: {
                color: "#5BE9FF",
                fontSize: "12",
              },
            },
            data: yData,
          },
        ],
        series: [
          {
            name: "人数",
            type: "bar",
            zlevel: 1,
            itemStyle: {
              normal: {
                barBorderRadius: [30, 30, 30, 30],
                color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                  {
                    offset: 0,
                    color: "#0049E5",
                  },
                  {
                    offset: 1,
                    color: "#00F6EC",
                  },
                ]),
                shadowBlur: 0,
                shadowColor: "rgba(87,220,222,0.7)",
              },
            },
            barWidth: 8,
            data: yData,
          },
          {
            name: "背景",
            type: "bar",
            barWidth: 8,
            barGap: "-100%",
            data: bgData,
            itemStyle: {
              normal: {
                color: "#041028",
                borderColor: "rgba(0,246,236,0.5)",
                barBorderRadius: [30, 30, 30, 30],
                shadowBlur: 3,
              },
            },
          },
        ],
      };
      myChart.setOption(option4);
    },
    // 物业服务人员进度条
    progress() {
      let myChart = echarts.init(this.$refs.option5);
      var echartData = [
        {
          name: "",
          data: [35.4],
        },
        {
          name: "",
          data: [28.6],
        },
        {
          name: "",
          data: [36],
        },
      ];

      if (
        this.management == "0.0" &&
        this.maintenance == "0.0" &&
        this.security == "0.0"
      ) {
        echartData = [
          {
            name: "",
            data: [0],
          },
          {
            name: "",
            data: [0],
          },
          {
            name: "",
            data: [0],
          },
        ];
      } else if (this.management == "0.0") {
        echartData = [
          {
            name: "",
            data: [28.6],
          },
          {
            name: "",
            data: [36],
          },
        ];
        echartData[0].data = [this.maintenance];
        echartData[1].data = [this.security];
      } else if (this.maintenance == "0.0") {
        echartData = [
          {
            name: "",
            data: [28.6],
          },
          {
            name: "",
            data: [36],
          },
        ];
        echartData[0].data = [this.management];
        echartData[1].data = [this.security];
      } else if (this.security == "0.0") {
        echartData = [
          {
            name: "",
            data: [28.6],
          },
          {
            name: "",
            data: [36],
          },
        ];
        echartData[0].data = [this.management];
        echartData[1].data = [this.maintenance];
      }
      // echartData[0].data = [this.management];
      let option5 = {
        //  backgroundColor: '#fff',
        grid: {
          containLabel: true,
          left: 16,
          right: 20,
          top: 26,
          //  bottom: 40,
        },
        tooltip: {
          //提示
          confine: true,
          trigger: "item",
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "line", // 默认为直线，可选为：'line' | 'shadow'
            label: {
              show: false,
            },
          },
        },
        xAxis: {
          axisLine: {
            show: false,
          },
          axisTick: {
            show: false,
          },
          splitLine: {
            show: false,
          },
          axisLabel: {
            show: false,
          },
        },
        yAxis: {
          data: [""],
          axisLabel: {
            show: false,
          },
          axisLine: {
            show: false,
          },
          axisTick: {
            show: false,
          },
          splitLine: {
            show: false,
          },
        },
        series: [],
      };

      echartData.forEach((item, index) => {
        option5.series.push({
          type: "bar",
          stack: "sum",
          barWidth: 10,
          name: item.name,
          data: item.data,
          label: {
            normal: {
              show: true,
              formatter:
                "{a|" + item.name + "} {b|" + item.data + "} {c|" + "%" + "}",
              align: "left",
              position: [0, -18, 0, 0],
              textStyle: {
                color: "#fff",
                rich: {
                  a: {
                    fontSize: "12",
                    fontWeight: 500,
                    color: "#fff",
                  },
                  b: {
                    fontSize: "14",
                    fontWeight: 500,
                    color: "#fff",
                  },
                  c: {
                    fontSize: "12",
                    fontWeight: 500,
                    color: "#fff",
                  },
                },
              },
            },
          },
        });
        if (index === 0) {
          option5.series[index].itemStyle = {
            normal: {
              barBorderRadius: [10, 0, 0, 10],
              color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                {
                  offset: 0,
                  color: "#1890FF",
                },
                {
                  offset: 1,
                  color: "#2ADFFC",
                },
              ]),
            },
          };
        } else if (index === echartData.length - 1) {
          option5.series[index].itemStyle = {
            normal: {
              barBorderRadius: [0, 10, 10, 0],
              color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                {
                  offset: 0,
                  color: "#DEFB3F",
                },
                {
                  offset: 1,
                  color: "#4AD672",
                },
              ]),
            },
          };
        } else {
          option5.series[index].itemStyle = {
            normal: {
              barBorderRadius: [0, 0, 0, 0],
              color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                {
                  offset: 0,
                  color: "#F07A15",
                },
                {
                  offset: 1,
                  color: "#F7CE04",
                },
              ]),
            },
          };
        }
      });
      myChart.setOption(option5);
    },
    // 访客统计折线图
    discount(dayData, visitList) {
      let max = 0;
      for (let i in visitList) {
        max = max >= visitList[i] ? max : visitList[i];
      }
      let myChart = echarts.init(this.$refs.option6);
      let option6 = {
        // backgroundColor: '#394056',
        tooltip: {
          trigger: "axis",
          confine: true,
          axisPointer: {
            lineStyle: {
              color: "#57617B",
            },
          },
        },
        legend: {
          icon: "rect",
          itemWidth: 14,
          itemHeight: 5,
          itemGap: 13,
          data: ["访客人次"],
          top: 28,
          right: "center",
          textStyle: {
            fontSize: 12,
            color: "#F1F1F3",
          },
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: false,
            axisLine: {
              lineStyle: {
                color: "#5BE9FF",
              },
            },
            data: dayData,
          },
          {
            axisPointer: {
              show: false,
            },
            axisLine: {
              lineStyle: {
                color: "#5BE9FF",
              },
            },
            axisTick: {
              show: false,
            },

            position: "bottom",
            offset: 20,
          },
        ],
        yAxis: [
          {
            type: "value",
            name: "单位：次",
            // data: [1, 2, 3],
            min: 0,
            max: 60,
            axisTick: {
              show: false,
            },
            axisLine: {
              lineStyle: {
                color: "#5BE9FF",
              },
              show: false,
              // data: [1, 2, 3],
            },

            axisLabel: {
              margin: 10,
              textStyle: {
                fontSize: 12,
              },
            },
            splitLine: {
              lineStyle: {
                color: "#174b6a",
              },
            },
          },
        ],
        series: [
          {
            name: "访客人次",
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                width: 2,
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
                      color: "rgba(14, 222, 233, 0.6)",
                    },
                    {
                      offset: 0.8,
                      color: "rgba(14, 222, 233, 0.1)",
                    },
                  ],
                  false
                ),
                shadowColor: "rgba(0, 0, 0, 0.1)",
                shadowBlur: 10,
              },
            },
            itemStyle: {
              normal: {
                color: "rgb(12, 218, 229)",
                borderColor: "rgba(137,189,2,0.27)",
                borderWidth: 12,
              },
            },
            data: visitList,
          },
        ],
      };
      option6.yAxis[0].max = max;
      myChart.setOption(option6);
    },
    // 立体渐变柱状图
    gradient(dayData, visitList) {
      let max = 0;
      for (let i in visitList) {
        max = max >= visitList[i] ? max : visitList[i];
      }
      let myChart = echarts.init(this.$refs.option7);
      let option7 = {
        textStyle: {
          fontSize: 0,
        },
        color: ["#00FCFF", "#FFDF1C", "#5AB72C", "#FF715A", "#2F9BFF"],
        xAxis: {
          nameTextStyle: {
            color: "#c0c3cd",
            padding: [0, 0, -10, 0],
            fontSize: 14,
          },
          axisLabel: {
            color: "#5BE9FF", //X轴文字
            fontSize: 12,
            interval: 0,
          },
          splitLine: {
            show: false,
          },
          axisLine: {
            lineStyle: {
              color: "#246480",
              width: 1,
              type: "solid",
            },
            show: true,
          },
          data: dayData,
          type: "category",
        },
        yAxis: {
          type: "value",
          name: "单位：次",
          min: 0,
          max: 100,
          // splitNumber: 6,
          nameTextStyle: {
            color: "#5BE9FF",
            // padding: [0, -6, -1, 0],
            fontSize: 14,
          },
          axisLabel: {
            color: "#5BE9FF", //Y轴文字
            fontSize: 12,
          },
          axisTick: {
            lineStyle: {
              color: "#384267",
              width: 1,
            },
            show: true,
          },
          splitLine: {
            show: false,
            lineStyle: {
              color: "#384267",
              type: "line",
            },
          },
          axisLine: {
            lineStyle: {
              color: "#246480",
              width: 1,
              type: "solid",
            },
            show: true,
          },
        },
        series: [
          {
            data: visitList,
            type: "bar",
            barMaxWidth: "20",
            barWidth: 16,
            itemStyle: {
              color: {
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                type: "linear",
                global: false,
                colorStops: [
                  {
                    offset: 0,
                    color: "#00FCFF",
                  },
                  {
                    offset: 1,
                    color: "#0B1A39",
                  },
                ],
              },
            },
            label: {
              show: false,
              distance: 10,
              color: "#fff",
              position: [30, 10],
            },
          },
        ],
        tooltip: {
          trigger: "axis",
          confine: true,
          show: false,
        },
      };
      option7.yAxis.max = max;
      myChart.setOption(option7);
    },
    // 社区人行折线图
    polyline() {
      let myChart = echarts.init(this.$refs.option8);
      let option8 = {
        // backgroundColor: '#394056',
        tooltip: {
          trigger: "axis",
          confine: true,
          axisPointer: {
            lineStyle: {
              color: "#57617B",
            },
          },
        },
        legend: {
          icon: "rect",
          itemWidth: 14,
          itemHeight: 5,
          itemGap: 13,
          data: ["人行", "车行"],
          top: 30,
          right: "center",
          textStyle: {
            fontSize: 12,
            color: "#F1F1F3",
          },
        },
        grid: {
          left: "3%",
          right: "4%",
          // top: "-2%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: false,
            axisLine: {
              lineStyle: {
                color: "#5BE9FF",
              },
            },
            data: [
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
              "13",
              "14",
              "15",
              "16",
              "17",
              "18",
              "19",
              "20",
              "21",
              "22",
              "23",
              "24",
              "25",
              "26",
              "27",
              "28",
              "29",
              "30",
              "31",
            ],
          },
          {
            axisPointer: {
              show: false,
            },
            axisLine: {
              lineStyle: {
                color: "#5BE9FF",
              },
            },
            axisTick: {
              show: false,
            },

            position: "bottom",
            offset: 20,
          },
        ],
        yAxis: [
          {
            type: "value",
            name: "单位：次",

            // data: [1, 2, 3],
            min: 0,
            max: 2000,
            nameTextStyle: {
              padding: [0, 44, -1, 0],
            },
            axisTick: {
              show: false,
            },
            axisLine: {
              lineStyle: {
                color: "#5BE9FF",
              },
              show: false,
              // data: [1, 2, 3],
            },

            axisLabel: {
              margin: 10,
              textStyle: {
                fontSize: 14,
              },
            },
            splitLine: {
              lineStyle: {
                color: "#174b6a",
              },
            },
          },
        ],
        series: [
          {
            name: "人行",
            type: "line",
            smooth: false,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                width: 1,
              },
            },
            itemStyle: {
              normal: {
                color: "#FF9E00",
                // borderColor: 'rgba(137,189,2,0.27)',
                // borderWidth: 12
              },
            },
            data: [
              0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
              0, 0, 0, 0, 0, 0, 0, 0, 0,
            ],
          },
          {
            name: "车行",
            type: "line",
            smooth: false,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                width: 1,
              },
            },
            itemStyle: {
              normal: {
                color: "#FF0040",
                // borderColor: 'rgba(219,50,51,0.2)',
                // borderWidth: 12
              },
            },
            data: [
              0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
              0, 0, 0, 0, 0, 0, 0, 0, 0,
            ],
          },
        ],
      };

      if (this.userVehicles != null) {
        option8.series[0].data = this.count3;
        option8.series[1].data = this.count4;
      } else {
        option8.series[0].data = [
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0,
        ];
        option8.series[1].data = [
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0,
        ];
      }
      if (this.maxSix > this.maxSeven) {
        option8.yAxis[0].max = this.maxSix;
      } else {
        option8.yAxis[0].max = this.maxSeven;
      }
      option8.xAxis[0].data = this.days;
      myChart.setOption(option8);
    },

    // 社区使用资源季度统计
    quarterly() {
      let myChart = echarts.init(this.$refs.option10);
      let option10 = {
        // backgroundColor: '#00265f',
        tooltip: {
          trigger: "axis",
          confine: true,
          axisPointer: {
            type: "shadow",
          },
        },
        legend: {
          // data: ["电量", "暖气量", "水量", "燃气量"],
          data: ["电量", "水量", "燃气量"],
          align: "right",
          right: "center",
          top: 46,
          textStyle: {
            color: "#fff",
          },
          itemWidth: 10,
          itemHeight: 10,
          itemGap: 35,
        },
        grid: {
          left: "6%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            // name: "(季节)",
            data: [
              "春\n(三、四、五月)",
              "夏\n(六、七、八月)",
              "秋\n(九、十、十一月)",
              "冬\n(十二、一、二月)",
            ],
            axisLine: {
              show: true,
              lineStyle: {
                color: "#5BE9FF",
                width: 1,
                type: "solid",
              },
            },

            axisTick: {
              show: false,
            },
            axisLabel: {
              show: true,
              textStyle: {
                color: "#00c7ff",
              },
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            name: "m³",
            nameTextStyle: {
              padding: [0, -42, -1, 0],
            },
            min: 0,
            max: 560,
            position: "right",
            axisLine: {
              show: false,
              lineStyle: {
                color: "#48bbd4",
              },
            },
            show: false,
            splitLine: {
              //网格线
              show: false,
            },
            axisTick: {
              show: false,
            },
            axisLabel: {
              formatter: "{value} ",
              textStyle: {
                color: "#00FF7F", //更改坐标轴文字颜色
              },
            },
          },
          {
            type: "value",
            name: "m³",
            nameTextStyle: {
              padding: [0, -48, -1, 0],
            },
            min: 0,
            max: 560,
            position: "right",
            offset: 40,
            axisLine: {
              show: true,
              lineStyle: {
                color: "#48bbd4",
              },
            },
            splitLine: {
              //网格线
              show: false,
            },
            axisLabel: {
              formatter: "{value} ",
              textStyle: {
                color: "#00FF7F", //更改坐标轴文字颜色
              },
            },
          },
          {
            type: "value",
            name: "GJ",
            nameTextStyle: {
              padding: [0, 42, -1, 0],
            },
            min: 0,
            max: 350,
            splitNumber: 6,
            position: "left",
            axisLine: {
              show: false,
              lineStyle: {
                color: "#f40",
              },
            },
            show: false,
            splitLine: {
              //网格线
              show: false,
            },
            axisTick: {
              show: false,
            },
            axisLabel: {
              formatter: "{value} ",
              textStyle: {
                color: "#FF3C00", //更改坐标轴文字颜色
              },
            },
          },
          {
            type: "value",
            name: "KW/h",
            nameTextStyle: {
              padding: [0, 48, -1, 0],
            },
            min: 0,
            max: 700,
            splitNumber: 6,
            offset: 40,
            position: "left",
            axisLine: {
              show: true,
              lineStyle: {
                color: "#48bbd4",
              },
            },
            splitLine: {
              //网格线
              show: false,
            },
            axisLabel: {
              formatter: "{value} ",
              textStyle: {
                color: "#FFBD00", //更改坐标轴文字颜色
              },
            },
          },
        ],
        series: [
          {
            name: "电量",
            type: "bar",
            data: [300, 300, 80, 58],
            yAxisIndex: 3,
            barWidth: 10, //柱子宽度
            barGap: 1, //柱子之间间距
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {
                    offset: 0,
                    color: "#FFBD00",
                  },
                  {
                    offset: 1,
                    color: "#FFBD00",
                  },
                ]),
              },
            },
          },
          // {
          //   name: "暖气量",
          //   type: "bar",
          //   data: [200, 200, 60, 61],
          //   yAxisIndex: 2,
          //   barWidth: 10,
          //   barGap: 1,
          //   itemStyle: {
          //     normal: {
          //       color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          //         {
          //           offset: 0,
          //           color: "#FF3C00"
          //         },
          //         {
          //           offset: 1,
          //           color: "#FF3C00"
          //         }
          //       ]),
          //       opacity: 1
          //     }
          //   }
          // },
          {
            name: "水量",
            type: "bar",
            data: [400, 400, 73, 68],
            yAxisIndex: 0,
            barWidth: 10,
            barGap: 1,
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {
                    offset: 0,
                    color: "#00FF7F",
                  },
                  {
                    offset: 1,
                    color: "#00FF7F",
                  },
                ]),
                opacity: 1,
              },
            },
          },
          {
            name: "燃气量",
            type: "bar",
            data: [100, 100, 73, 68],
            yAxisIndex: 1,
            barWidth: 10,
            barGap: 1,
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {
                    offset: 0,
                    color: "#0083FF",
                  },
                  {
                    offset: 1,
                    color: "#0083FF",
                  },
                ]),
                opacity: 1,
              },
            },
          },
        ],
      };

      myChart.setOption(option10);
    },

    // 今日气象折线图
    weather() {
      let myChart = echarts.init(this.$refs.option12);
      let option12 = {
        tooltip: {},
        grid: {
          top: "28%",
          left: "0",
          right: "1%",
          bottom: "8%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: true,
            axisLine: {
              //坐标轴轴线相关设置。数学上的x轴
              show: false,
              lineStyle: {
                color: "#f9f9f9",
              },
            },
            axisLabel: {
              //坐标轴刻度标签的相关设置
              textStyle: {
                color: "#d1e6eb",
                margin: 15,
              },
            },
            axisTick: {
              show: false,
            },
            // data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', ],
          },
        ],
        yAxis: [
          {
            type: "value",
            min: 0,
            max: 40,
            // splitNumber: 7,
            splitLine: {
              show: false,
              lineStyle: {
                color: "#0a3256",
              },
            },
            axisLine: {
              show: false,
            },
            axisLabel: {
              margin: 10,
              textStyle: {
                color: "#5BE9FF",
              },
              formatter: "{value}°",
            },
            axisTick: {
              show: false,
            },
          },
        ],
        series: [
          {
            name: "气温",
            type: "line",
            // smooth: true, //是否平滑曲线显示
            // 			symbol:'circle',  // 默认是空心圆（中间是白色的），改成实心圆
            showAllSymbol: true,
            symbol: "emptyCircle",
            symbolSize: 6,
            lineStyle: {
              normal: {
                color: "#28ffb3", // 线条颜色
              },
              borderColor: "#f0f",
            },
            label: {
              show: true,
              position: "top",
              formatter: "{c}°",
              textStyle: {
                color: "#5BE9FF",
              },
            },
            itemStyle: {
              normal: {
                color: "#28ffb3",
              },
            },
            tooltip: {
              show: false,
            },
            data: [17, 18, 18, 18, 18, 18],
          },
        ],
      };
      myChart.setOption(option12);
    },
    // 空气质量折线图
    air() {
      let myChart = echarts.init(this.$refs.option13);
      let option13 = {
        // backgroundColor: '#394056',
        tooltip: {
          trigger: "axis",
          confine: true,
          axisPointer: {
            lineStyle: {
              color: "#57617B",
            },
          },
        },
        legend: {
          icon: "rect",
          itemWidth: 14,
          itemHeight: 5,
          itemGap: 13,
          data: ["平均优良率", "旭辉·湖山原著"],
          top: 28,
          right: "20",
          textStyle: {
            fontSize: 12,
            color: "#F1F1F3",
          },
        },
        grid: {
          left: "4%",
          right: "4%",
          top: "18%",
          bottom: "13%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: true,
            // name: "(日期)",
            axisLine: {
              lineStyle: {
                color: "#5BE9FF",
              },
            },
            axisLabel: {
              // interval: 0,
              // rotate: "-45"
              // color: '#c1cadf',
            },
            data: ["6月", "7月", "8月", "9月", "10月"],
          },
        ],
        yAxis: [
          {
            type: "value",
            name: "单位：μg/m³",
            // data: [1, 2, 3],
            nameTextStyle: {
              padding: [0, 12, -1, 0],
            },
            min: 0,
            max: 1000,
            axisTick: {
              show: true,
            },
            axisLine: {
              lineStyle: {
                color: "#5BE9FF",
              },
              show: false,
              // data: [1, 2, 3],
            },

            axisLabel: {
              margin: 10,
              // rotate: "-45",
              textStyle: {
                fontSize: 12,
              },
            },
            splitLine: {
              lineStyle: {
                color: "#174b6a",
              },
            },
          },
        ],
        series: [
          {
            name: "平均优良率",
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                width: 2,
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
                      color: "rgba(14, 222, 233, 0.6)",
                    },
                    {
                      offset: 0.8,
                      color: "rgba(14, 222, 233, 0.1)",
                    },
                  ],
                  false
                ),
                shadowColor: "rgba(0, 0, 0, 0.1)",
                shadowBlur: 10,
              },
            },
            itemStyle: {
              normal: {
                color: "rgb(12, 218, 229)",
                borderColor: "rgba(137,189,2,0.27)",
                borderWidth: 12,
              },
            },
            data: [250, 447, 545, 346, 745],
          },
          {
            name: "旭辉·湖山原著",
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                width: 2,
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
                      color: "rgba(255,185,72, 0.6)",
                    },
                    {
                      offset: 0.8,
                      color: "rgba(255,185,72, 0.1)",
                    },
                  ],
                  false
                ),
                shadowColor: "rgba(0, 0, 0, 0.1)",
                shadowBlur: 10,
              },
            },
            itemStyle: {
              normal: {
                color: "rgb(255,185,72)",
                borderColor: "rgba(137,189,2,0.27)",
                borderWidth: 12,
              },
            },
            data: [450, 287, 387, 786, 345],
          },
        ],
      };
      myChart.setOption(option13);
    },
    //各设备统计
    statisticsOfEachEquipment() {
      let myChart = echarts.init(this.$refs.equipment);
      var color = [
        "#1890FF",
        "#13B68D",
        "#B8B559",
        "#64BA65",
        "#47FFFF",
        "#4BA5FF",
        "#00BFA5",
      ];
      var title = "";
      var legend = [
        // 'A需求类型',
        // 'B需求类型',
        // 'C需求类型',
        // 'D需求类型',
        // 'E需求类型',
        // '其他'
      ];
      var seriesData = [
        { name: "监控", value: 30 },
        { name: "电梯", value: 10 },
        { name: "道闸", value: 15 },
        { name: "单元门可视", value: 23 },
        { name: "消防传感器", value: 10 },
        { name: "门禁", value: 12 },
      ];
      seriesData[0].value = this.barrier.camera || 0;
      seriesData[1].value = this.barrier.elevator || 0;
      seriesData[2].value = this.barrier.barrier || 0;
      seriesData[3].value = this.barrier.visual || 0;
      seriesData[4].value = this.barrier.sensor || 0;
      seriesData[5].value = this.barrier.access + this.barrier.cloudAccess || 0;
      console.log(seriesData, "seriesData");
      var equipment = {
        // backgroundColor:'#050e31',
        color: color,
        title: {
          top: 20,
          text: title,
          textStyle: {
            fontSize: 10,
            color: "#DDEEFF",
          },
        },
        tooltip: {
          confine: true,
          formatter: "{b}: {c}",
        },
        grid: {
          top: "15%",
          left: 0,
          right: "1%",
          bottom: 5,
          containLabel: true,
        },
        legend: {
          orient: "vertical",
          top: "center",
          right: 50,
          textStyle: {
            align: "left",
            verticalAlign: "middle",
            rich: {
              name: {
                color: "rgba(255,255,255,0.5)",
                fontSize: 10,
              },
              value: {
                color: "rgba(255,255,255,0.5)",
                fontSize: 10,
              },
              rate: {
                color: "rgba(255,255,255,0.9)",
                fontSize: 10,
              },
            },
          },
          data: legend,
          formatter: (name) => {
            if (seriesData.length) {
              const item = seriesData.filter((item) => item.name === name)[0];
              return `{name|${name}：}{value| ${item.value}} {rate| ${item.value}%}`;
            }
          },
        },
        series: [
          {
            itemStyle: {
              normal: {
                // borderWidth: 5,
                // borderColor: "rgba(8,12,30, 0.9)"
              },
            },
            name: "",
            type: "pie",
            center: ["20%", "45%"],
            radius: ["40%", "55%"],
            label: {
              normal: {
                show: false,
                position: "center",
                formatter: "{value|{c}}\n{label|{b}}",
                rich: {
                  value: {
                    padding: 5,
                    align: "center",
                    verticalAlign: "middle",
                    fontSize: 10,
                  },
                  label: {
                    align: "center",
                    verticalAlign: "middle",
                    fontSize: 10,
                  },
                },
              },
              emphasis: {
                show: true,
                textStyle: {
                  fontSize: "10",
                },
              },
            },
            labelLine: {
              show: false,
              length: 0,
              length2: 0,
            },
            data: seriesData,
          },
        ],
      };
      myChart.setOption(equipment);
      this.dynamic(myChart, 6);
    },
    // 园区警告
    warningPark() {
      // this.warningPark1();
      // this.warningPark2();
      let myChart = echarts.init(this.$refs.warning);
      var angle = [220, -40],
        curVal = 67.8;
      let warning = {
        tooltip: {
          show: false,
          confine: true,
          formatter: "{a} <br/>{b} : {c}%",
        },
        series: [
          {
            name: "最外层环仪表盘",
            type: "gauge",
            min: 0,
            max: 100,
            radius: "69%",
            startAngle: angle[0],
            endAngle: angle[1],
            splitNumber: 10,
            axisLine: {
              lineStyle: {
                color: [[1, "#c1c3c5"]],
                width: 10,
              },
            },
            axisTick: {
              lineStyle: {
                color: "#fff",
                width: 2,
              },
              length: 0,
              splitNumber: 1,
            },
            axisLabel: {
              distance: -40,
              formatter: function (v) {
                if (v == 60) {
                  return "{a|" + v + "}";
                } else if (v == 85) {
                  return "{b|" + v + "}";
                } else {
                  return v;
                }
              },
              textStyle: {
                color: "#bbb",
              },
              rich: {
                a: {
                  color: "#fbe010",
                },
                b: {
                  color: "#3fa7dc",
                },
              },
            },
            splitLine: {
              show: true,
              length: 20,
              lineStyle: {
                color: "#fff",
                width: 1,
              },
            },
            itemStyle: {
              normal: {
                color: "#818488",
                shadowColor: "rgba(0, 0, 0, 0.5)",
                shadowBlur: 1,
              },
            },
            detail: {
              formatter: "{value}分",
              offsetCenter: [0, "60%"],
              textStyle: {
                fontSize: 15,
                color: "#333",
              },
            },
            title: {
              show: false,
            },
            pointer: {
              length: "65%",
            },
            data: [
              {
                value: curVal,
              },
            ],
          },
          {
            name: "内环仪表盘四段颜色",
            type: "gauge",
            min: 0,
            max: 100,
            radius: "59%",
            startAngle: angle[0],
            endAngle: angle[1],
            splitNumber: 1,
            axisLine: {
              lineStyle: {
                color: [
                  [0.6, "#fb5310"],
                  [0.85, "#fbe010"],
                  [1, "#3fa7dc"],
                ],
                width: 10,
              },
            },
            axisTick: {
              show: false,
            },
            axisLabel: {
              show: false,
            },
            splitLine: {
              show: true,
              length: 5,
              lineStyle: {
                color: "#fff",
                width: 1,
              },
            },
            pointer: {
              length: 0,
            },
            detail: {
              show: false,
            },
          },
        ],
      };
      setInterval(function () {
        const random = +(Math.random() * 60).toFixed(0);
        myChart.setOption({
          series: [
            {
              data: [
                {
                  value: random,
                },
              ],
            },
            {
              data: [
                {
                  value: random,
                },
              ],
            },
          ],
        });
      }, 2000);
      myChart.setOption(warning);
    },
    // 园区警告
    warningPark1() {
      let myChart = echarts.init(this.$refs.warning1);
      var angle = [220, -40],
        curVal = 67.8;
      let warning1 = {
        tooltip: {
          show: false,
          confine: true,
          formatter: "{a} <br/>{b} : {c}%",
        },
        series: [
          {
            name: "最外层环仪表盘",
            type: "gauge",
            min: 0,
            max: 100,
            radius: "69%",
            startAngle: angle[0],
            endAngle: angle[1],
            splitNumber: 10,
            axisLine: {
              lineStyle: {
                color: [[1, "#c1c3c5"]],
                width: 10,
              },
            },
            axisTick: {
              lineStyle: {
                color: "#fff",
                width: 2,
              },
              length: 0,
              splitNumber: 1,
            },
            axisLabel: {
              distance: -40,
              formatter: function (v) {
                if (v == 60) {
                  return "{a|" + v + "}";
                } else if (v == 85) {
                  return "{b|" + v + "}";
                } else {
                  return v;
                }
              },
              textStyle: {
                color: "#bbb",
              },
              rich: {
                a: {
                  color: "#fbe010",
                },
                b: {
                  color: "#3fa7dc",
                },
              },
            },
            splitLine: {
              show: true,
              length: 20,
              lineStyle: {
                color: "#fff",
                width: 1,
              },
            },
            itemStyle: {
              normal: {
                color: "#818488",
                shadowColor: "rgba(0, 0, 0, 0.5)",
                shadowBlur: 1,
              },
            },
            detail: {
              formatter: "{value}分",
              offsetCenter: [0, "60%"],
              textStyle: {
                fontSize: 15,
                color: "#333",
              },
            },
            title: {
              show: false,
            },
            pointer: {
              length: "65%",
            },
            data: [
              {
                value: curVal,
              },
            ],
          },
          {
            name: "内环仪表盘四段颜色",
            type: "gauge",
            min: 0,
            max: 100,
            radius: "59%",
            startAngle: angle[0],
            endAngle: angle[1],
            splitNumber: 1,
            axisLine: {
              lineStyle: {
                color: [
                  [0.6, "#fb5310"],
                  [0.85, "#fbe010"],
                  [1, "#3fa7dc"],
                ],
                width: 10,
              },
            },
            axisTick: {
              show: false,
            },
            axisLabel: {
              show: false,
            },
            splitLine: {
              show: true,
              length: 5,
              lineStyle: {
                color: "#fff",
                width: 1,
              },
            },
            pointer: {
              length: 0,
            },
            detail: {
              show: false,
            },
          },
        ],
      };
      setInterval(function () {
        const random = +(Math.random() * 60).toFixed(0);
        myChart.setOption({
          series: [
            {
              data: [
                {
                  value: random,
                },
              ],
            },
            {
              data: [
                {
                  value: random,
                },
              ],
            },
          ],
        });
      }, 2000);
      myChart.setOption(warning1);
    },
    // 园区警告
    warningPark2() {
      let myChart = echarts.init(this.$refs.warning2);
      var angle = [220, -40],
        curVal = 67.8;
      let warning2 = {
        tooltip: {
          show: false,
          confine: true,
          formatter: "{a} <br/>{b} : {c}%",
        },
        series: [
          {
            name: "最外层环仪表盘",
            type: "gauge",
            min: 0,
            max: 100,
            radius: "69%",
            startAngle: angle[0],
            endAngle: angle[1],
            splitNumber: 10,
            axisLine: {
              lineStyle: {
                color: [[1, "#c1c3c5"]],
                width: 10,
              },
            },
            axisTick: {
              lineStyle: {
                color: "#fff",
                width: 2,
              },
              length: 0,
              splitNumber: 1,
            },
            axisLabel: {
              distance: -40,
              formatter: function (v) {
                if (v == 60) {
                  return "{a|" + v + "}";
                } else if (v == 85) {
                  return "{b|" + v + "}";
                } else {
                  return v;
                }
              },
              textStyle: {
                color: "#bbb",
              },
              rich: {
                a: {
                  color: "#fbe010",
                },
                b: {
                  color: "#3fa7dc",
                },
              },
            },
            splitLine: {
              show: true,
              length: 20,
              lineStyle: {
                color: "#fff",
                width: 1,
              },
            },
            itemStyle: {
              normal: {
                color: "#818488",
                shadowColor: "rgba(0, 0, 0, 0.5)",
                shadowBlur: 1,
              },
            },
            detail: {
              formatter: "{value}分",
              offsetCenter: [0, "60%"],
              textStyle: {
                fontSize: 15,
                color: "#333",
              },
            },
            title: {
              show: false,
            },
            pointer: {
              length: "65%",
            },
            data: [
              {
                value: curVal,
              },
            ],
          },
          {
            name: "内环仪表盘四段颜色",
            type: "gauge",
            min: 0,
            max: 100,
            radius: "59%",
            startAngle: angle[0],
            endAngle: angle[1],
            splitNumber: 1,
            axisLine: {
              lineStyle: {
                color: [
                  [0.6, "#fb5310"],
                  [0.85, "#fbe010"],
                  [1, "#3fa7dc"],
                ],
                width: 10,
              },
            },
            axisTick: {
              show: false,
            },
            axisLabel: {
              show: false,
            },
            splitLine: {
              show: true,
              length: 5,
              lineStyle: {
                color: "#fff",
                width: 1,
              },
            },
            pointer: {
              length: 0,
            },
            detail: {
              show: false,
            },
          },
        ],
      };
      setInterval(function () {
        const random = +(Math.random() * 60).toFixed(0);
        myChart.setOption({
          series: [
            {
              data: [
                {
                  value: random,
                },
              ],
            },
            {
              data: [
                {
                  value: random,
                },
              ],
            },
          ],
        });
      }, 2000);
      myChart.setOption(warning2);
    },
    // 小区设备告警统计
    allocationDelay() {
      let myChart = echarts.init(this.$refs.allocation);
      let data = [100000, 100000, 100000, 100000];
      let maxDate = [
        Math.max(...data),
        Math.max(...data),
        Math.max(...data),
        Math.max(...data),
      ];
      // let myColor = [
      //   "#eb2100",
      //   "#eb3600",
      //   "#d0570e",
      //   "#d0a00e",
      //   "#34da62",
      //   "#00e9db",
      //   "#00c0e9",
      //   "#0096f3",
      //   "#33CCFF",
      //   "#33FFCC"
      // ];
      let myColor = ["#47ffff"];
      // let myColor = [
      //   "#eb2",
      //   "#eb3",
      //   "#d05",
      //   "#d0a",
      // ];
      let allocation = {
        grid: {
          left: "12%",
          top: "20%",
          right: "4%",
          bottom: "-4%",
          containLabel: true,
        },
        xAxis: [
          {
            show: false,
          },
        ],
        yAxis: [
          {
            axisTick: "none",
            axisLine: "none",
            offset: "27",
            // offset: "50",
            axisLabel: {
              textStyle: {
                color: "#47ffff",

                fontSize: "13",
              },
            },
            data: ["门禁设备", "火点检测", "视频设备", "可视对讲"],
          },
          {
            axisTick: "none",
            axisLine: "none",
            axisLabel: {},
            data: [4, 6, 12, 10000],
          },
          {
            type: "category",
            // axisTick: "none",
            axisTick: {
              show: false,
            },
            position: "right",
            axisLine: {
              show: false,
            },
            axisLabel: {
              textStyle: {
                color: "white",
                fontSize: 12,
              },
              formatter: (params) => {
                return params;
              },
            },
            data: [1000, 6, 12, 10000],
          },
        ],
        series: [
          {
            name: "条",
            type: "bar",
            yAxisIndex: 0,
            data: [4, 6, 12, 10000],
            // 修改标签位置
            label: {
              normal: {
                show: false,
                // 定位标签位置
                position: "insideRight",
                // distance: -1500,

                textStyle: {
                  color: "white",
                  fontSize: "16",
                },
              },
            },
            // 第二种定位标签位置方法
            // labelLayout(params) {
            //   return {
            //     x: "93%",
            //     y: params.rect.y + params.rect.height / 2,
            //     verticalAlign: "middle",
            //     alingn: "left",
            //   };
            // },
            barWidth: 12,
            itemStyle: {
              normal: {
                color: function (params) {
                  var num = myColor.length;
                  return myColor[params.dataIndex % num];
                },
                barBorderRadius: 7,
              },
            },
            z: 2,
          },
          {
            name: "白框",
            type: "bar",
            yAxisIndex: 1,
            barGap: "-100%",
            data: maxDate,

            barWidth: 10,
            itemStyle: {
              normal: {
                color: "#0e2147",
                barBorderRadius: 5,
              },
            },
            z: 1,
          },
          {
            name: "外框",
            type: "bar",
            yAxisIndex: 2,
            barGap: "-100%",

            data: maxDate,
            barWidth: 14,
            itemStyle: {
              normal: {
                color: function (params) {
                  var num = myColor.length;
                  return myColor[params.dataIndex % num];
                },
                barBorderRadius: 5,
              },
            },
            z: 0,
          },
          {
            name: "外圆",
            type: "scatter",
            hoverAnimation: false,
            data: [4, 0, 0, 0],
            yAxisIndex: 2,
            symbolSize: 14,
            itemStyle: {
              normal: {
                color: function (params) {
                  var num = myColor.length;
                  return myColor[params.dataIndex % num];
                },
                opacity: 1,
              },
            },
            z: 2,
          },
        ],
      };
      allocation.yAxis[2].data = this.arrFive;
      allocation.yAxis[1].data = this.arrFive;
      allocation.series[0].data = this.arrFive;
      // allocation.series[3].data = this.arrFive;

      console.log(this.arrFive, "this.arrFive;");
      myChart.setOption(allocation);
      // console.log(allocation.series[3].data, "allocation.series[3].data");
    },
    // 工单统计
    statisticsWorkOrder() {
      let myChart = echarts.init(this.$refs.statistics);
      var getValue = [219, 241, 174, 544, 435, 882, 354];
      getValue = this.workOrderSum;
      var getName = [
        "报事报修",
        "装修评定",
        "清洁工单",
        "巡查",
        "巡更",
        "巡检",
        "抄表",
      ];
      var max = Math.max.apply(null, getValue);
      var getMax = [];
      let str;
      for (let i = 0; i < getName.length; i++) {
        getMax.push(max);
      }

      let statistics = {
        // backgroundColor: '#030A41',
        grid: {
          left: "10%",
          right: "0%",
          bottom: "0%",
          top: "10%",
          containLabel: true,
        },

        // tooltip: {
        //   trigger: "axis",
        //   axisPointer: {
        //     type: "none",
        //   },
        //   formatter: function (params) {
        //     return params[0].name + " : " + params[0].value;
        //   },
        // },
        xAxis: {
          show: false,
          type: "value",
        },
        yAxis: [
          {
            type: "category",
            inverse: true,
            offset: 50,
            // 左边文字间距和颜色
            axisLabel: {
              show: true,
              align: "left",
              textStyle: {
                color: "#66cc00",
              },
              formatter: function (value, index) {
                str = "{title|" + value + "}";
                return str;
              },
              rich: {
                title: {
                  color: "#64BDFF",
                  fontSize: 10,
                },
              },
            },
            // splitLine: {
            //   show: false,
            // },
            // 轴线
            axisTick: {
              show: false,
            },
            axisLine: {
              show: false,
            },
            data: getName,
          },
          {
            type: "category",
            inverse: true,
            offset: -10,
            axisTick: "none",
            axisLine: "none",
            show: true,
            axisLabel: {
              textStyle: {
                color: "#ffffff",
                fontSize: "10",
              },
              // 多少次的文字
              formatter: function (value, index) {
                str = "{title|" + value + "次}";
                return str;
              },
              rich: {
                title: {
                  color: "#1DB9FF",
                  fontSize: 10,
                },
              },
            },
            data: getValue,
          },
        ],
        series: [
          {
            name: "值",
            type: "bar",
            zlevel: 1,
            itemStyle: {
              normal: {
                barBorderRadius: 10,
                // color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                //     {
                //         offset: 0,
                //         color: '#B3F3FF',
                //     },
                //     {
                //         offset: 1,
                //         color: '#1DB9FF',
                //     },
                // ]),
                color: (params) => {
                  // build a color map as your need.
                  //定义一个颜色集合
                  var colorList = ["#0080FF", "#ffcb48", "#12c9fe", "#1DB9FF"];
                  var colorList2 = ["#FFD4FF", "#ff900e", "#0599de", "#B3F3FF"];
                  //对每个bar显示一种颜色
                  var idx = params.dataIndex < 1 ? params.dataIndex : 3;
                  var colorS = colorList[idx];
                  var colorS2 = colorList2[idx];
                  return new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                    { offset: 0, color: colorS },
                    { offset: 1, color: colorS2 },
                  ]);
                },
              },
            },
            barWidth: 12,
            data: getValue,
          },
          {
            name: "背景",
            type: "bar",
            barWidth: 12,
            barGap: "-100%",
            data: getMax,
            itemStyle: {
              normal: {
                color: "#334F78",
                barBorderRadius: 12,
              },
            },
          },
        ],
      };
      myChart.setOption(statistics);
      this.dynamic(myChart, 6);
    },
    // 一键处理
    statisticsApply() {
      let myChart = echarts.init(this.$refs.applye);

      let applye = {
        tooltip: {
          trigger: "axis",
          show: false,
          confine: true,
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
          },
        },
        legend: {
          show: false,
        },
        grid: {
          left: "0%",
          right: "30%",
          top: "1%",
          bottom: "1%",
          containLabel: true,
        },
        xAxis: [
          {
            splitLine: {
              show: false,
            },
            type: "value",
            show: false,
          },
        ],
        yAxis: [
          {
            splitLine: {
              show: false,
            },
            axisLine: {
              //y轴
              show: false,
            },
            type: "category",
            axisTick: {
              show: false,
            },
            inverse: true,
            data: ["一", "二", "三", "四", "五", "六", "日", "本周"],
            axisLabel: {
              margin: 4,
              color: "#A7D6F4",
              fontSize: 6,
            },
          },
        ],
        series: [
          {
            name: "标准化",
            type: "bar",
            barWidth: 5, // 柱子宽度
            label: {
              show: true,
              position: "right", // 位置
              color: "#A7D6F4",
              fontSize: 10,
              distance: 5, // 距离
              formatter: "{c} 件", // 这里是数据展示的时候显示的数据
            }, // 柱子上方的数值
            itemStyle: {
              barBorderRadius: [0, 20, 20, 0], // 圆角（左上、右上、右下、左下）

              color: new echarts.graphic.LinearGradient(
                1,
                0,
                0,
                0,
                [
                  {
                    offset: 0,
                    color: "#C83F25",
                  },
                  {
                    offset: 1,
                    color: "#AE8F5E",
                  },
                ],
                false
              ), // 渐变
            },
            data: [400, 380, 360, 340, 320, 300, 280, 260],
          },
        ],
      };
      applye.series[0].data = this.weeks;
      myChart.setOption(applye);
    },

    // 智慧电能
    electricEnergy() {
      let myChart = echarts.init(this.$refs.option22);

      let index = 0;
      var colorList = ["#00feff", "#00fe65", "#ff821c"];
      function fun() {
        var timer = setInterval(function () {
          myChart.dispatchAction({
            type: "hideTip",
            seriesIndex: 0,
            dataIndex: index,
          });
          // 显示提示框
          myChart.dispatchAction({
            type: "showTip",
            seriesIndex: 0,
            dataIndex: index,
          });
          // 取消高亮指定的数据图形
          myChart.dispatchAction({
            type: "downplay",
            seriesIndex: 0,
            dataIndex: index == 0 ? 5 : index - 1,
          });
          myChart.dispatchAction({
            type: "highlight",
            seriesIndex: 0,
            dataIndex: index,
          });
          index++;
          if (index > 5) {
            index = 0;
          }
        }, 3000);
      }

      fun();
      setTimeout(function () {
        fun();
      }, 5000);
      let option22 = {
        title: {
          text: "",
          x: "center",
          y: "center",
          textStyle: {
            fontSize: 20,
          },
        },
        tooltip: {
          confine: true,
          trigger: "item",
        },
        series: [
          {
            type: "pie",
            center: ["50%", "58%"],
            radius: ["24%", "45%"],
            clockwise: true,
            avoidLabelOverlap: true,
            hoverOffset: 15,
            itemStyle: {
              normal: {
                color: function (params) {
                  return colorList[params.dataIndex];
                },
              },
            },
            label: {
              show: true,
              position: "outside",
              formatter: "{a|{b}：\n{d}%}\n{hr|}",
              rich: {
                hr: {
                  // backgroundColor: 't',
                  // borderRadius: 3,
                  // width: 3,
                  // height: 1,
                  // padding: [3, 3, 0, -12]
                },
                a: {
                  padding: [-30, 1, -20, 1],
                  lineHeight: 16,
                },
              },
            },
            labelLine: {
              normal: {
                length: 10,
                length2: 16,
                lineStyle: {
                  width: 2,
                },
              },
            },
            data: [
              {
                name: "占比",
                value: 48,
              },
              {
                name: "占比",
                value: 42,
              },
              {
                name: "占比",
                value: 10,
              },
            ],
          },
        ],
      };

      myChart.setOption(option22);
    },

    // 设备状态统计
    stateStatistics() {
      let myChart = echarts.init(this.$refs.stateStatistics);
      let stateStatistics = {
        tooltip: {
          confine: true,
          trigger: "item",
          formatter: "{a} <br/>{b}: {c}",
        },
        legend: {
          bottom: 2,
          data: ["在线数", "离线数"],
          textStyle: {
            color: "#fff",
            fontSize: 10,
          },
        },
        series: [
          {
            name: "设备状态",
            type: "pie",
            radius: ["50%", "70%"],
            avoidLabelOverlap: false,
            hoverAnimation: false,
            label: {
              show: false,
              position: "center",
            },
            emphasis: {
              label: {
                show: false,
                fontSize: "10",
                fontWeight: "bold",
              },
            },
            labelLine: {
              show: false,
            },
            data: [
              {
                value: 305,
                name: "在线数",
                itemStyle: {
                  normal: {
                    color: "#1890FF",
                  },
                },
              },
              {
                value: 31,
                name: "离线数",
                itemStyle: {
                  normal: {
                    color: "#F4A305",
                  },
                },
              },
            ],
          },
        ],
      };
      stateStatistics.series[0].data[0].value = this.online;
      stateStatistics.series[0].data[1].value = this.offline;
      myChart.setOption(stateStatistics);
      this.dynamic(myChart, 2);
    },
    // 年度报事报修
    repairReportOne() {
      let myChart = echarts.init(this.$refs.repairReport);
      let data = [
        {
          name: "报事报修",
          value: 100,
        },
        {
          name: "保洁",
          value: 75,
        },
        {
          name: "工程维修",
          value: 50,
        },
        {
          name: "投诉建议",
          value: 25,
        },
        {
          name: "其他",
          value: 100,
        },
      ];
      data[0].value = this.parkRepair.reportRepair;
      data[1].value = this.parkRepair.clean;
      data[2].value = this.parkRepair.project;
      data[3].value = this.parkRepair.complaint;
      data[4].value = this.parkRepair.other;
      let arrName = getArrayValue(data, "name");
      let arrValue = getArrayValue(data, "value");
      let sumValue = eval(arrValue.join("+"));
      let objData = array2obj(data, "name");
      let optionData = getData(data);

      function getArrayValue(array, key) {
        var key = key || "value";
        var res = [];
        if (array) {
          array.forEach(function (t) {
            res.push(t[key]);
          });
        }
        return res;
      }

      function array2obj(array, key) {
        var resObj = {};
        for (var i = 0; i < array.length; i++) {
          resObj[array[i][key]] = array[i];
        }
        return resObj;
      }

      function getData(data) {
        var res = {
          series: [],
          yAxis: [],
        };
        for (let i = 0; i < data.length; i++) {
          res.series.push({
            name: "",
            type: "pie",
            clockWise: false, //顺时加载
            hoverAnimation: false, //鼠标移入变大
            radius: [70 - i * 5 + "%", 68 - i * 5 + "%"],
            center: ["33%", "55%"],
            label: {
              show: false,
            },
            itemStyle: {
              label: {
                show: false,
              },
              labelLine: {
                show: false,
              },
              borderWidth: 5,
            },
            data: [
              {
                value: data[i].value * 0.75,
                name: data[i].name,
              },
              {
                value: 100 - data[i].value * 0.75,
                name: "",
                itemStyle: {
                  color: "rgba(0,0,0,0)",
                  borderWidth: 5,
                },
                tooltip: {
                  show: false,
                },
                hoverAnimation: false,
              },
            ],
          });
          res.series.push({
            name: "",
            type: "pie",
            silent: true,
            z: 1,
            clockWise: false, //顺时加载
            hoverAnimation: false, //鼠标移入变大
            radius: [70 - i * 5 + "%", 68 - i * 5 + "%"],
            center: ["33%", "55%"],
            label: {
              show: false,
            },
            itemStyle: {
              label: {
                show: false,
              },
              labelLine: {
                show: false,
              },
              borderWidth: 5,
            },
            data: [
              {
                value: 7.5,
                itemStyle: {
                  color: "rgb(182,180,176,0.3)",
                  borderWidth: 5,
                },
                tooltip: {
                  show: false,
                },
                hoverAnimation: false,
              },
              {
                value: 2.5,
                name: "",
                itemStyle: {
                  color: "rgba(182,180,176,0)",
                  borderWidth: 0,
                },
                tooltip: {
                  show: false,
                },
                hoverAnimation: false,
              },
            ],
          });
          res.yAxis.push(((data[i].value / sumValue) * 100).toFixed(2) + "%");
        }
        return res;
      }

      let repairReport = {
        tooltip: {
          // show: true,
          confine: true,
          trigger: "item",
          formatter: "{b}:{c}件",
        },
        legend: {
          show: true,
          icon: "circle",
          top: "16%",
          bottom: "10%",
          left: "35%",
          data: arrName,
          itemHeight: 5,
          width: 0,
          padding: [0, 0],
          itemGap: 0,
          formatter: function (name) {
            return (
              "{title|" +
              name +
              "} {value|" +
              objData[name].value +
              "}  {title|件}"
            );
          },

          textStyle: {
            rich: {
              title: {
                fontSize: 10,
                lineHeight: 0,
                color: "rgb(0, 178, 246)",
              },
              value: {
                fontSize: 10,
                lineHeight: 0,
                color: "#fff",
              },
            },
          },
        },

        color: [
          "rgb(9,187,247)",
          "rgb(184,254,165)",
          "rgb(253,218,23)",
          "rgb(252,152,12)",
        ],
        xAxis: [
          {
            show: false,
          },
        ],
        series: optionData.series,
      };
      myChart.setOption(repairReport);
      //  this.dynamic(myChart, 7);
    },
    // 年度维修统计
    maintenanceAnnual() {
      let myChart = echarts.init(this.$refs.repairAnnual);
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
      line = this.maintenanceEquipmentVos;
      // var attr ={
      //     'name':'维修',
      //     'unit':'次'
      // }
      var graphicData = ["维修次数"];
      // var colors = []
      let repairAnnual = {
        tooltip: {
          show: true,
          confine: true,
          appendToBody: true,
          trigger: "item",
          formatter: "{b}月维修:{c}次",
        },
        //   tooltip: {
        //           trigger: 'item',
        //         padding: 1,
        //         formatter: function(param) {

        //             var resultTooltip =
        //                 "<div style='background:rgba(13,5,30,.6);border:1px solid rgba(255,255,255,.2);padding:5px;border-radius:3px;'>" +
        //                 "<div style='text-align:center;'>" +  param.name + "</div>" +
        //                 "<div style='padding-top:5px;'>"+
        //                 "<span style=''> " +attr.name + ": </span>" +
        //                 "<span style=''>" + param.value + "</span><span>" + attr.unit + "</span>"+
        //                 "</div>" +
        //                 "</div>";
        //             return resultTooltip
        //         }
        //         },
        graphic: [
          {
            type: "text",
            z: 100,
            left: "10%",
            top: "15%",
            style: {
              fill: "#81A5E8",
              text: graphicData.join("\n"),
              padding: [20, 20],
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
          // itemGap: 30,
          textStyle: {
            // color: '#FFFFFF'
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
              show: false,
              lineStyle: {
                color: "#eee",
                type: "solid",
              },
            },
            axisTick: {
              show: false,
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
            //柱底圆片
            name: "",
            type: "pictorialBar",
            symbolSize: [15, 5],
            symbolOffset: [0, 3],
            z: 12,
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {
                    offset: 0,
                    color: "rgba(89,211,255,1)",
                  },
                  {
                    offset: 1,
                    color: "rgba(23,237,194,1)",
                  },
                ]),
              },
            },
            data: line,
          },

          //柱体
          {
            name: "",
            type: "bar",
            barWidth: 15,
            barGap: "0%",
            itemStyle: {
              normal: {
                color: {
                  x: 0,
                  y: 0,
                  x2: 0,
                  y2: 1,
                  type: "linear",
                  global: false,
                  colorStops: [
                    {
                      //第一节下面
                      offset: 0,
                      color: "rgba(0,255,245,0.5)",
                    },
                    {
                      offset: 1,
                      color: "#43bafe",
                    },
                  ],
                },
              },
            },

            data: line,
          },

          //柱顶圆片
          {
            name: "",
            type: "pictorialBar",
            symbolSize: [15, 5],
            symbolOffset: [0, -3],
            z: 12,
            symbolPosition: "end",
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                  0,
                  0,
                  0,
                  1,
                  [
                    {
                      offset: 0,
                      color: "rgba(89,211,255,1)",
                    },
                    {
                      offset: 1,
                      color: "rgba(23,237,194,1)",
                    },
                  ],
                  false
                ),
              },
            },
            data: line,
          },
        ],
      };
      // line = this.maintenanceEquipmentVos
      repairAnnual.yAxis[0].max = this.maxEight;
      myChart.setOption(repairAnnual);
      this.dynamic(myChart, 12);
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
          confine: true,
          formatter: "{b}月:{c}吨",
        },
        graphic: [
          {
            type: "text",
            z: 100,
            left: "6%",
            top: "5%",
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
    // 论坛词云
    communityForum() {
      let myChart = echarts.init(this.$refs.forum);
      let forum = {
        tooltip: {
          show: true,
          confine: true,
          formatter: "{b}",
        },
        series: [
          {
            type: "wordCloud",
            gridSize: 1,
            shape: "diamond",
            sizeRange: [10, 25],
            width: 400,
            height: 400,
            textStyle: {
              normal: {
                color: function () {
                  return (
                    "rgb(" +
                    [
                      Math.round(Math.random() * 160),
                      Math.round(Math.random() * 160),
                      Math.round(Math.random() * 160),
                    ].join(",") +
                    ")"
                  );
                },
              },
              emphasis: {
                shadowBlur: 10,
                shadowColor: "#333",
              },
            },
            data: [
              {
                name: " 没有",
                value: 30,
              },
              {
                name: " 屏幕",
                value: 24,
              },
              {
                name: " 不错",
                value: 21,
              },
              {
                name: " 可以",
                value: 19,
              },
              {
                name: " 发货",
                value: 18,
              },
              {
                name: " 这个",
                value: 18,
              },
              {
                name: " 什么",
                value: 17,
              },
              {
                name: " 一个",
                value: 12,
              },
              {
                name: " 不好",
                value: 12,
              },
              {
                name: " 质量",
                value: 11,
              },
              {
                name: " 快递",
                value: 11,
              },
              {
                name: " 问题",
                value: 10,
              },
              {
                name: " 物流",
                value: 9,
              },
              {
                name: " 几天",
                value: 9,
              },
              {
                name: " 一般",
                value: 9,
              },
              {
                name: " 就是",
                value: 9,
              },
              {
                name: " 使用",
                value: 8,
              },
              {
                name: " 怎么",
                value: 8,
              },
              {
                name: " 电池",
                value: 8,
              },
              {
                name: " 不能",
                value: 8,
              },
              {
                name: " 速度",
                value: 8,
              },
              {
                name: " 客服",
                value: 8,
              },
              {
                name: " 一星",
                value: 8,
              },
              {
                name: " 拍照",
                value: 8,
              },
              {
                name: " 摄像头",
                value: 7,
              },
              {
                name: " 外观",
                value: 7,
              },
              {
                name: " 包装",
                value: 7,
              },
              {
                name: " 到货",
                value: 7,
              },
              {
                name: " 有点",
                value: 7,
              },
              {
                name: " 抢购",
                value: 6,
              },
              {
                name: " 这样",
                value: 6,
              },
              {
                name: " 性价比",
                value: 6,
              },
              {
                name: " 一般般",
                value: 6,
              },
              {
                name: " 出现",
                value: 6,
              },
              {
                name: " 以前",
                value: 6,
              },
              {
                name: " 知道",
                value: 6,
              },
              {
                name: " 一次",
                value: 6,
              },
              {
                name: " 真的",
                value: 6,
              },
              {
                name: " 收到",
                value: 6,
              },
              {
                name: " 其他",
                value: 6,
              },
              {
                name: " 电量",
                value: 6,
              },
              {
                name: " 还是",
                value: 6,
              },
              {
                name: " 不行",
                value: 6,
              },
              {
                name: " 红米",
                value: 6,
              },
              {
                name: " 那么",
                value: 6,
              },
              {
                name: " 挺快",
                value: 5,
              },
              {
                name: " 现在",
                value: 5,
              },
              {
                name: " 服务",
                value: 5,
              },
              {
                name: " 充电",
                value: 5,
              },
              {
                name: " 流畅",
                value: 5,
              },
              {
                name: " 系统",
                value: 5,
              },
              {
                name: " 非常",
                value: 5,
              },
              {
                name: " 满意",
                value: 5,
              },
              {
                name: " 购物",
                value: 5,
              },
              {
                name: " 玩游戏",
                value: 5,
              },
              {
                name: " 信号",
                value: 5,
              },
              {
                name: " 不了",
                value: 5,
              },
              {
                name: " 别人",
                value: 5,
              },
              {
                name: " 死机",
                value: 5,
              },
              {
                name: " 耗电",
                value: 5,
              },
              {
                name: " 一直",
                value: 5,
              },
              {
                name: " 像素",
                value: 5,
              },
              {
                name: " 第一次",
                value: 5,
              },
              {
                name: " 广告",
                value: 5,
              },
              {
                name: " 买个",
                value: 5,
              },
              {
                name: " 黑屏",
                value: 5,
              },
              {
                name: " 为什么",
                value: 4,
              },
              {
                name: " 猴王",
                value: 4,
              },
              {
                name: " 视频",
                value: 4,
              },
              {
                name: " 体验",
                value: 4,
              },
              {
                name: " 好评",
                value: 4,
              },
              {
                name: " 闪屏",
                value: 4,
              },
              {
                name: " 价格",
                value: 4,
              },
              {
                name: " 饥饿",
                value: 4,
              },
              {
                name: " 营销",
                value: 4,
              },
              {
                name: " 发热",
                value: 4,
              },
              {
                name: " 还行",
                value: 4,
              },
              {
                name: " 一天",
                value: 4,
              },
              {
                name: " 需要",
                value: 4,
              },
              {
                name: " 特别",
                value: 4,
              },
              {
                name: " 而且",
                value: 4,
              },
              {
                name: " 运行",
                value: 4,
              },
              {
                name: " 等到",
                value: 4,
              },
              {
                name: " 手机卡",
                value: 4,
              },
              {
                name: " 呵呵",
                value: 4,
              },
              {
                name: " 时候",
                value: 4,
              },
              {
                name: " 东西",
                value: 4,
              },
              {
                name: " 感觉",
                value: 4,
              },
              {
                name: " 卡机",
                value: 4,
              },
              {
                name: " 没什么",
                value: 4,
              },
              {
                name: " 大家",
                value: 4,
              },
              {
                name: " 没货",
                value: 4,
              },
              {
                name: " 售后",
                value: 4,
              },
              {
                name: " 比较",
                value: 4,
              },
              {
                name: " 退货",
                value: 4,
              },
              {
                name: " 加价",
                value: 3,
              },
              {
                name: " 耳机",
                value: 3,
              },
              {
                name: " 呜呜",
                value: 3,
              },
              {
                name: " 一分钱",
                value: 3,
              },
              {
                name: " 一分货",
                value: 3,
              },
              {
                name: " 默认",
                value: 3,
              },
              {
                name: " 说好",
                value: 3,
              },
              {
                name: " 你们",
                value: 3,
              },
              {
                name: " 一点",
                value: 3,
              },
              {
                name: " 超级",
                value: 3,
              },
              {
                name: " 耐用",
                value: 3,
              },
              {
                name: " 很快",
                value: 3,
              },
              {
                name: " 断流",
                value: 3,
              },
              {
                name: " 套装",
                value: 3,
              },
              {
                name: " 一样",
                value: 3,
              },
              {
                name: " 发票",
                value: 3,
              },
              {
                name: " 买回来",
                value: 3,
              },
              {
                name: " 软件",
                value: 3,
              },
              {
                name: " 还好",
                value: 3,
              },
              {
                name: " 以后",
                value: 3,
              },
              {
                name: " 下单",
                value: 3,
              },
              {
                name: " 打游戏",
                value: 3,
              },
              {
                name: " 我们",
                value: 3,
              },
              {
                name: " 但是",
                value: 3,
              },
              {
                name: " 一部",
                value: 3,
              },
              {
                name: " 王者",
                value: 3,
              },
              {
                name: " 不怎么样",
                value: 3,
              },
              {
                name: " 看看",
                value: 3,
              },
              {
                name: " 自己",
                value: 3,
              },
              {
                name: " 里面",
                value: 3,
              },
              {
                name: " 边框",
                value: 3,
              },
              {
                name: " 看着",
                value: 3,
              },
              {
                name: " 要死",
                value: 3,
              },
              {
                name: " 原因",
                value: 3,
              },
              {
                name: " 新机",
                value: 3,
              },
              {
                name: " 不如",
                value: 3,
              },
              {
                name: " 老是",
                value: 3,
              },
              {
                name: " 购买",
                value: 3,
              },
              {
                name: " 更好",
                value: 3,
              },
              {
                name: " 相机",
                value: 3,
              },
              {
                name: " 竟然",
                value: 3,
              },
              {
                name: " 一分",
                value: 3,
              },
              {
                name: " 天才",
                value: 3,
              },
              {
                name: " 荣耀",
                value: 3,
              },
              {
                name: " 正常",
                value: 3,
              },
              {
                name: " 回复",
                value: 3,
              },
              {
                name: " 联系",
                value: 3,
              },
              {
                name: " 总体",
                value: 3,
              },
              {
                name: " 反应",
                value: 3,
              },
              {
                name: " 老爸",
                value: 3,
              },
              {
                name: " 星期",
                value: 3,
              },
              {
                name: " 半个",
                value: 3,
              },
              {
                name: " 打电话",
                value: 3,
              },
              {
                name: " 不是",
                value: 3,
              },
              {
                name: " 保护膜",
                value: 3,
              },
              {
                name: " 充电器",
                value: 3,
              },
              {
                name: " 买不到",
                value: 2,
              },
              {
                name: " 老子",
                value: 2,
              },
              {
                name: " 差评",
                value: 2,
              },
              {
                name: " 这么",
                value: 2,
              },
              {
                name: " 真香",
                value: 2,
              },
              {
                name: " 真是",
                value: 2,
              },
              {
                name: " 三天",
                value: 2,
              },
              {
                name: " 填写内容",
                value: 2,
              },
              {
                name: " 帮别人",
                value: 2,
              },
              {
                name: " 终于",
                value: 2,
              },
              {
                name: " 有时候",
                value: 2,
              },
              {
                name: " 极致",
                value: 2,
              },
              {
                name: " 还有",
                value: 2,
              },
              {
                name: " 希望",
                value: 2,
              },
              {
                name: " 必须",
                value: 2,
              },
              {
                name: " 好像",
                value: 2,
              },
              {
                name: " 严重",
                value: 2,
              },
              {
                name: " 反应速度",
                value: 2,
              },
              {
                name: " 后面",
                value: 2,
              },
              {
                name: " 音乐",
                value: 2,
              },
              {
                name: " 商品",
                value: 2,
              },
              {
                name: " 哈哈",
                value: 2,
              },
              {
                name: " 垃圾",
                value: 2,
              },
              {
                name: " 好用",
                value: 2,
              },
              {
                name: " 突出",
                value: 2,
              },
              {
                name: " 价钱",
                value: 2,
              },
              {
                name: " 耍猴",
                value: 2,
              },
              {
                name: " 磕碰",
                value: 2,
              },
              {
                name: " 几次",
                value: 2,
              },
              {
                name: " 失灵",
                value: 2,
              },
              {
                name: " 支持",
                value: 2,
              },
              {
                name: " 快充",
                value: 2,
              },
              {
                name: " 意思",
                value: 2,
              },
              {
                name: " 失败",
                value: 2,
              },
              {
                name: " 整整",
                value: 2,
              },
              {
                name: " 没收",
                value: 2,
              },
              {
                name: " 已经",
                value: 2,
              },
              {
                name: " 无法",
                value: 2,
              },
              {
                name: " 方面",
                value: 2,
              },
              {
                name: " 为啥",
                value: 2,
              },
              {
                name: " 喜欢",
                value: 2,
              },
              {
                name: " 套餐",
                value: 2,
              },
              {
                name: " 完全",
                value: 2,
              },
              {
                name: " 专业",
                value: 2,
              },
              {
                name: " 选择",
                value: 2,
              },
              {
                name: " 48mp",
                value: 2,
              },
              {
                name: " 无线",
                value: 2,
              },
              {
                name: " 一个月",
                value: 2,
              },
              {
                name: " 不过",
                value: 2,
              },
              {
                name: " ldquo",
                value: "-",
              },
              {
                name: " rdquo",
                value: "-",
              },
              {
                name: " 这种",
                value: 2,
              },
              {
                name: " 送货",
                value: 2,
              },
              {
                name: " 卡死",
                value: 2,
              },
              {
                name: " 来看",
                value: 2,
              },
              {
                name: " 糊弄人",
                value: 2,
              },
              {
                name: " note7",
                value: 2,
              },
              {
                name: " 时间",
                value: 2,
              },
              {
                name: " 短信",
                value: 2,
              },
              {
                name: " 滑落",
                value: 2,
              },
              {
                name: " 那样",
                value: 2,
              },
              {
                name: " vivo",
                value: 2,
              },
              {
                name: " 防水",
                value: 2,
              },
              {
                name: " 等待",
                value: 2,
              },
              {
                name: " 直播",
                value: 2,
              },
              {
                name: " 难受",
                value: 2,
              },
              {
                name: " 四个",
                value: 2,
              },
              {
                name: " 到手",
                value: 2,
              },
              {
                name: " 发现",
                value: 2,
              },
              {
                name: " 掉电",
                value: 2,
              },
              {
                name: " 华为",
                value: 2,
              },
              {
                name: " 再也",
                value: 2,
              },
              {
                name: " 不用",
                value: 2,
              },
              {
                name: " 给力",
                value: 2,
              },
              {
                name: " 两天",
                value: 2,
              },
              {
                name: " 立马",
                value: 2,
              },
              {
                name: " 两周",
                value: 2,
              },
              {
                name: " 处理器",
                value: 2,
              },
              {
                name: " 关机",
                value: 2,
              },
              {
                name: " 要么",
                value: 2,
              },
              {
                name: " 满血",
                value: 2,
              },
              {
                name: " 不会",
                value: 2,
              },
              {
                name: " 定金",
                value: 2,
              },
              {
                name: " 付款",
                value: 2,
              },
              {
                name: " 心情",
                value: 2,
              },
              {
                name: " 另外",
                value: 2,
              },
              {
                name: " 游戏",
                value: 2,
              },
              {
                name: " 公司",
                value: 2,
              },
              {
                name: " 按键",
                value: 2,
              },
              {
                name: " 卡顿",
                value: 2,
              },
              {
                name: " 晚上",
                value: 2,
              },
              {
                name: " 百分之",
                value: 2,
              },
              {
                name: " 手机信号",
                value: 2,
              },
              {
                name: " 一下",
                value: 2,
              },
              {
                name: " 开学",
                value: 2,
              },
              {
                name: " 上班",
                value: 2,
              },
              {
                name: " 上天",
                value: 2,
              },
              {
                name: " 配送",
                value: 2,
              },
              {
                name: " 最后",
                value: 2,
              },
              {
                name: " 太慢",
                value: 2,
              },
              {
                name: " 手机套",
                value: 2,
              },
              {
                name: " 笨重",
                value: 2,
              },
              {
                name: " 半年",
                value: 2,
              },
              {
                name: " 屏险",
                value: 2,
              },
              {
                name: " 先发",
                value: 2,
              },
              {
                name: " 不灵敏",
                value: 2,
              },
              {
                name: " 硬度",
                value: 2,
              },
              {
                name: " 这次",
                value: 2,
              },
              {
                name: " 性能",
                value: 2,
              },
              {
                name: " 介绍",
                value: 2,
              },
              {
                name: " 卡针",
                value: 2,
              },
              {
                name: " 不理",
                value: 2,
              },
              {
                name: " 这是",
                value: 2,
              },
              {
                name: " 买来",
                value: 2,
              },
              {
                name: " 两次",
                value: 2,
              },
              {
                name: " 网络",
                value: 2,
              },
              {
                name: " 就裂",
                value: 2,
              },
              {
                name: " 没用",
                value: 2,
              },
              {
                name: " 多次",
                value: 2,
              },
              {
                name: " 钢化",
                value: 2,
              },
              {
                name: " 一星怪",
                value: 1,
              },
              {
                name: " 新款",
                value: 1,
              },
              {
                name: " 你行",
                value: 1,
              },
              {
                name: " 没送",
                value: 1,
              },
              {
                name: " 一个多",
                value: 1,
              },
              {
                name: " 礼拜",
                value: 1,
              },
              {
                name: " 只能",
                value: 1,
              },
              {
                name: " 价位",
                value: 1,
              },
              {
                name: " 闪退",
                value: 1,
              },
              {
                name: " 抢到",
                value: 1,
              },
              {
                name: " 其它",
                value: 1,
              },
              {
                name: " 便宜",
                value: 1,
              },
              {
                name: " 两百",
                value: 1,
              },
              {
                name: " 盈利",
                value: 1,
              },
              {
                name: " 机智",
                value: 1,
              },
              {
                name: " 更新换代",
                value: 1,
              },
              {
                name: " 太乱",
                value: 1,
              },
              {
                name: " 赔付",
                value: 1,
              },
              {
                name: " 自营",
                value: 1,
              },
              {
                name: " 产品",
                value: 1,
              },
              {
                name: " 手持",
                value: 1,
              },
              {
                name: " 夜景",
                value: 1,
              },
              {
                name: " 机子",
                value: 1,
              },
              {
                name: " 拆过",
                value: 1,
              },
            ],
          },
        ],
      };
      myChart.setOption(forum);
      this.dynamic(myChart, 30);
    },
    // 小区人流量统计
    flowSingle(dayData, userData) {
      let max = 0;
      for (let i in userData) {
        max = max >= userData[i] ? max : userData[i];
      }
      let myChart = echarts.init(this.$refs.flow_single);
      let flow_single = {
        tooltip: {
          type: "cross",
          confine: true,
          trigger: "axis",
          formatter: function (params) {
            let name = params[0].name;
            let res = name + "：<br/>";
            for (let i = 0; i < params.length - 1; i++) {
              let series = params[i];
              res +=
                series.marker + series.seriesName + ":" + series.data + "<br/>";
            }
            return res;
          },
        },
        legend: {
          top: -5,
          itemWidth: 1,
          itemHeight: 8,
          itemGap: 10,
          left: "15%",
          textStyle: {
            color: " #3A90A2",
            fontSize: 10,
          },
          data: ["人行人次"],
        },
        grid: {
          top: "15%",
          left: "3%",
          right: "4%",
          bottom: "5%",
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
          data: dayData,
        },
        yAxis: {
          type: "value",
          name: "人行：（次）",
          min: 0,
          max: 240,
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
          axisTick: {
            show: false,
          },
          axisLabel: {
            formatter: "{value}",
            color: "#3F797F",
            fontSize: 8,
          },
          splitLine: {
            lineStyle: {
              color: "#174b6a",
            },
          },
          boundaryGap: ["20%", "20%"],
        },
        series: [
          {
            name: "人行次数",
            type: "line",
            // min:0,
            //             max: 100,
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
                // {
                //   xAxis: "08:00",
                //   name: "今日"
                // }
              ],
            },
            data: userData,
          },
          {
            name: "对比",
            type: "line",
            // min:0,
            //             max: 100,
            symbol: "none",
            itemStyle: {
              normal: {
                color: "#ccc", //图例的颜色
                lineStyle: {
                  type: "dotted", //'dotted'虚线 'solid'实线
                  width: 0,
                  color: "#77f0c4", //线的颜色
                },
              },
            },
            data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
          },
        ],
      };
      flow_single.yAxis.max = max;
      myChart.setOption(flow_single);
      this.dynamic(myChart, 7);
    },
    // 进出车流量统计
    flowDouble(dayData, vehicleInData, vehicleOutData) {
      let max = 0;
      for (let i in vehicleInData) {
        max = max >= vehicleInData[i] ? max : vehicleInData[i];
      }
      for (let i in vehicleOutData) {
        max = max >= vehicleOutData[i] ? max : vehicleOutData[i];
      }
      let myChart = echarts.init(this.$refs.flow_double);
      let flow_double = {
        tooltip: {
          type: "cross",
          confine: true,
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
          top: 30,
          itemWidth: 1,
          itemHeight: 8,
          itemGap: 10,
          left: "10%",
          textStyle: {
            color: " #3A90A2",
            fontSize: 10,
          },
          data: ["进入车辆", "驶出车辆"],
        },
        grid: {
          top: "25%",
          left: "3%",
          right: "4%",
          bottom: "5%",
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
          data: dayData,
        },
        yAxis: {
          type: "value",
          name: "车：（辆）",
          min: 0,
          max: 300,
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
          axisTick: {
            show: false,
          },
          axisLabel: {
            formatter: "{value}",
            color: "#3F797F",
            fontSize: 8,
          },
          splitLine: {
            lineStyle: {
              color: "#174b6a",
            },
          },
          boundaryGap: ["20%", "20%"],
        },
        series: [
          {
            name: "进入车辆",
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
                // {
                //   xAxis: "08:00",
                //   name: "今日"
                // }
              ],
            },
            data: vehicleInData,
          },
          {
            name: "驶出车辆",
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
            data: vehicleOutData,
          },
          {
            name: "对比",
            type: "line",
            symbol: "none",
            itemStyle: {
              normal: {
                color: "#ccc", //图例的颜色
                lineStyle: {
                  type: "dotted", //'dotted'虚线 'solid'实线
                  width: 0,
                  color: "#77f0c4", //线的颜色
                },
              },
            },
            data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
          },
        ],
      };
      flow_double.yAxis.max = max;
      myChart.setOption(flow_double);
      this.dynamic(myChart, 7);
    },

    //初始化监控
    cameraPlay(cameraList) {
      let that = this;
      that.playerShow = true;
      this.$nextTick(() => {
        let monitoringTop = this.$refs.monitoringTop;
        that.cameraWidth = Math.ceil(monitoringTop.clientWidth) + "px";
        that.cameraHeight = Math.ceil(monitoringTop.clientHeight) + "px";
        for (let i in cameraList) {
          if (cameraList[i].cameraUrl) {
            that.$refs.camera.preview(that.curIndex, cameraList[i].cameraUrl);
            that.curIndex += 1;
          }
          if (that.curIndex > 9) {
            break;
          }
        }
      });
    },

    //停止预览
    stopPlay() {
      let that = this;
      that.$nextTick(() => {
        if (undefined != that.$refs.camera) {
          that.$refs.camera.stopPlay(that.curIndex);
        }
        that.playerShow = false;
      });
    },
    //停止预览
    stopAllPlay() {
      let that = this;
      that.$nextTick(() => {
        if (undefined != that.$refs.camera) {
          that.$refs.camera.stopAllPlay();
        }
        that.playerShow = false;
      });
    },
  },
};
</script>
<style>
::-webkit-media-controls {
  display: none !important;
}

@media screen and (max-width: 991px) {
  #play_window {
    /* width: calc(20vw - 16px);
      height: calc((20vw - 16px) * 5 / 8); */
    width: 34rem;
    height: 20rem;
  }
}

@media screen and (min-width: 992px) {
  #play_window {
    /* width: calc(50vw - 8px);
      height: calc((50vw - 8px) * 5 / 8); */
    width: 34rem;

    height: 20rem;
  }
}
</style>
<style lang="scss">
@import "src/assets/common/common65v2";
// ::v-deep #play_window {
//   position: unset!important;
// }
// .backgroundMap {
//   width: 5760px;
//   height: 1080px;
// }
</style>
