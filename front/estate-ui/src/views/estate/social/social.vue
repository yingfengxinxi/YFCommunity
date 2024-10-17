<template>
  <div class="app-container">
    <!--信息管理-->
    <div v-show=checkView >
      <!--模糊查询-->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-form-item label="发布日期">
          <el-date-picker
            v-model="dateRange"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @keyup.enter.native="handleQuery"
          ></el-date-picker>
        </el-form-item>

        <el-form-item label="发布人员" prop="userName">
          <el-input
            v-model="queryParams.userName"
            placeholder="请输入发布人员"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>

<!--        <el-form-item label="所属小区" prop="userCommunityName">
          <el-input
            v-model="queryParams.userCommunityName"
            placeholder="请输入所属小区"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>-->


        <el-form-item label="所属小区" prop="communityId">
          <el-select v-model="queryParams.communityId"  placeholder="请选择小区" style="width: 100%">
            <el-option
              v-for="item in communityList"
              :key="item.communityId"
              :label="item.communityName"
              :value="item.communityId">
            </el-option>
          </el-select>
        </el-form-item>



        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜 索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重 置</el-button>
        </el-form-item>
      </el-form>
      <!--按钮操作-->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
          >新增
          </el-button>
        </el-col>
        
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
      <!--表单-->
      <el-table v-loading="loading" :data="socialList" row-key="socialId"  @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column label="发布内容" align="center" key="content" prop="content" :show-overflow-tooltip="true"/>
        <el-table-column label="发布人员" align="center" key="userName" prop="userName"/>
        <el-table-column label="所属小区" align="center" key="userCommunityName" prop="userCommunityName"/>
        <el-table-column label="发布时间" align="center" key="createTime" prop="createTime"/>

        <el-table-column
          label="操作"
          align="center"
          width="160"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="queryInfo(scope.row)"
      
            >查看
            </el-button>
            <el-button
              size="mini"
              type="text"
              style="color: #f56c6c"
              icon="el-icon-delete"

              @click="handleDeleteOne(scope.row)"
           
            >删除</el-button>
          </template>

        </el-table-column>

      </el-table>

      <!--     分页操作-->
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>

    <!--新增或修改对话框v-show="checkAddUpdateView"-->
    <el-dialog :title="title" :visible.sync="checkAddUpdateView" width="50%" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
          <el-form-item label="发布小区" prop="communityId">
            <el-select v-model="form.communityId"  placeholder="请选择小区" style="width: 70%">
              <el-option
                v-for="item in communityList"
                :key="item.communityId"
                :label="item.communityName"
                :value="item.communityId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布内容" prop="content">
            <el-input v-model="form.content" type="textarea" placeholder="请输入内容" style="width: 70%"  prop="content"></el-input>
          </el-form-item>
          <el-form-item  label="上传图片"  label-position="left">
            <image-upload  v-model="form.socialUrl" :limit="9"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

    <!--详情页-->
    <div :title="infoTitle" v-show=sysCheckInfo>
      <div slot="footer" class="clearInfo">
        <el-button @click="cancel" >取 消</el-button>
      </div>
      <div>
        <el-form ref="forms" :model="form"  label-width="100px">
          <h4 class="form-header">详情信息</h4>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="发布人员：" prop="createBy">
                <div>
                  {{form.userName}}
                </div>
              </el-form-item>
            </el-col>

            <el-col :span="6" :offset="2">
              <el-form-item label="发布小区：" prop="createBy">
                <span>{{form.userCommunityName }}</span>
              </el-form-item>
            </el-col>

          </el-row>



          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="发布内容：" prop="content">
                <div>
                  {{form.content}}
                </div>
              </el-form-item>
            </el-col>

            <el-col :span="6" :offset="2">
              <el-form-item label="点赞数量：" prop="likeAmount" >
                <span>{{this.likeAmount}}  人点赞</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :offset="1">
              <el-form-item label="发布图片：" >
                <image-preview v-for="image in form.socialUrls" :key="image" :src="image" :width="100" :height="100" style="margin: 0px 10px;"/>
              </el-form-item>
            </el-col>
          </el-row>




          <!--聊天页面-->
          <el-row>
            <div  @click="inputFocus" class="my-reply">
              <!-- <el-avatar class="header-img" :size="40" :src="avatar"></el-avatar> -->
              <div class="reply-info">
                <div
                  tabindex="0"
                  contenteditable="true"
                  id="replyInput"
                  spellcheck="false"
                  placeholder="输入评论..."
                  class="reply-input"
                  @input="onDivInput($event)"
                ></div>
              </div>
              <div class="reply-btn-box" >
                <el-button
                  class="reply-btn"
                  size="medium"
                  @click="sendComment"
                  type="primary"
                  
                >发表评论</el-button
                >
              </div>
            </div>
            <div class="comment">
              <div>
                <div
                  v-for="(item, i) in comments"
                  :key="i"
                  class="author-title reply-father"
                >
                  <el-avatar class="header-img" :size="40" :src="item.avatar"></el-avatar>
                  <div class="author-info" v-show="item.replyName!=undefined">
                    <span class="author-name">{{ item.accountName }} 回复 {{ item.replyName }} </span>
                    <span class="author-time">{{ item.createTime }}
                          <el-button
                          v-if="item.flag=='0'"
                            class="reply-btn"
                            size="small"
                            @click="revokeMsgHandle(i)"
                            type="primary"
                            style="margin-left: 10px;"
                         
                          >撤回</el-button>
                    </span>
                  </div>


                 
                  <div class="author-info" v-show="item.replyName==undefined">
                    <span class="author-name">{{ item.accountName }}  </span>
                    <span class="author-time">{{ item.createTime }}
                          <el-button
                            class="reply-btn"
                            size="small"
                            v-if="item.flag=='0'"
                            @click="revokeMsgHandle(i)"
                            type="primary"
                            style="margin-left: 10px;"
                          >撤回</el-button>
                    </span>
                  </div>

                  <div class="icon-btn">
                    <span @click="showReplyInput(i, item.accountName, item.commentId)">
                      <i class="iconfont el-icon-s-comment"></i>
                    </span>
                  </div>
                  <div class="talk-box">
                    <p>
                      <span class="reply"> {{ item.content }}</span>
                    </p>
                  </div>

                  <!--                  <div class="reply-box">
                                      <div
                                        v-for="(reply, j) in item.reply"
                                        :key="j"
                                        class="author-title">
                                        <el-avatar
                                          class="header-img"
                                          :size="40"
                                          :src="reply.avatar"
                                        ></el-avatar>
                                        <div class="author-info">
                                          <span class="author-name">{{ reply.accountName }}</span>
                                          <span class="author-time">{{ reply.createTime }}</span>
                                        </div>
                                        <div class="icon-btn">
                                          <span @click="showReplyInput(i, reply.accountName, reply.id)">
                                              <i class="iconfont el-icon-s-comment"/>
                                          </span>
                                        </div>
                                        <div class="talk-box">
                                          <p>
                                            回复<span> @{{ reply.replyName }}: </span>
                                            <span class="reply"> {{ reply.content }}</span>
                                          </p>
                                        </div>
                                        <div class="reply-box"></div>
                                      </div>
                                    </div>-->
                  <div v-show="handlerInputShow(i)" class="my-reply my-comment-reply">
                   

                    <div class="reply-info">
                      <div
                        tabindex="0"
                        contenteditable="true"
                        spellcheck="false"
                        :placeholder="replyName"
                        @input="onDivInput($event)"
                        class="reply-input reply-comment-input"
                      ></div>
                    </div>
                    <div class="reply-btn-box">
                      <el-button
                        class="reply-btn"
                        size="medium"
                        @click="sendCommentReply(i)"
                        type="primary"
                   
                      >发表回复</el-button
                      >
                    </div>

                  </div>



                </div>
              </div>
            </div>




          </el-row>
        </el-form>
      </div>

      <div slot="footer" class="clearInfo">
        <el-button @click="cancel" >取 消</el-button>
      </div>
    </div>

  </div>
</template>

<script>
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {getCurrentList} from "@/api/estate/community";
import {desensitization, equals} from "@/utils/common";
import {
  addSocial,
  delMessageSocial,
  delSystemSocial,
  getSocialList,
  getSystemList,
  querySocialById,
  updateSocial
} from "@/api/estate/social/social";
import {addComment, getCommentList, queryLikeAmount,revokeMsg} from "@/api/estate/social/comment";
export default {
  name: "social",
  data() {
    return {
      checkPhoto:false,
      communityNameList:[],
      //↓↓↓下面的是测试↓↓↓
      replyName: "", // 回复的对象（父评论）用户名
      replyComment: "", // 评论输入内容
      accountName: "", // 直评用户名称
      socialId:"",
      avatar: "", // 登录用户头像
      loginName:"",
      index: "0",
      commentId: "",
      comments: [],
      //↑↑↑上面的是测试↑↑↑
     
      // 表单校验
      rules: {
        communityId: [
          {required: true, message: "请选择发布小区", trigger: "blur"},
        ],
        content: [
          {required: true, message: "请输入发布内容", trigger: "blur"},
        ],
        socialUrl: [
          {required: true, message: "请上传图片", trigger: "blur"},
        ],
      },
      selectA:false,
      selectB:true,

      //直评：true，追评false
      commentIf:true,

      //物业数据
      estateList:[],
      checkAddUpdateView: false,
      //小区数据
      communityList: [],
      //详情页控制显隐
      sysCheckInfo: false,
      // 选中数组
      ids: [],
      //选中名字数组
      names: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //证件类型字典
      certificateOptions: [],
      // 日期范围
      dateRange: [],
      // 弹出层标题（用户导入）
      title: "",
      //模糊查询显隐
      showSearch: true,
      //模糊查询显隐
      sysShowSearch: true,
      //查询到的列表参数
      socialList: [],
      //新增提交按钮显隐
      close: true,
      //详情页显隐
      checkView: true,

      //系统信息显隐
      checkSysView:false,

      likeAmount:0,
      // 遮罩层
      loading: true,

      //系统信息遮罩层
      sysLoading: true,

      //详情页的抬头
      infoTitle:"",

      //存储系统信息数据
      sysSocialList:[],

      //系统管理中的总条数
      sysTotal:0,
      // 总条数
      total: 0,
      // 模糊查询数据
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        communityId: undefined,
      },
      querySysParams:{
        pageNum: 1,
        pageSize: 10,
        createBy: undefined,
        userCommunityName: undefined,
      },
      addCommentForm:{
        socialId:"",
        replyId:"",
        content:undefined,
      },

      revokeMsgForm:{
        createTime:undefined,
        commentId:undefined
      },

      // 表单参数
      form: {},
    };
  },
  created() {
    this.getList();
    this. getCommunitys();
    console.log("议事堂")
  },
  methods: {
    /** 撤回功能*/
    revokeMsgHandle(i){
      this.revokeMsgForm.createTime =this.comments[i].createTime;
      this.revokeMsgForm.commentId =this.comments[i].commentId;
      revokeMsg( this.revokeMsgForm).then(response => {
        //查询当前评论列表
        this.getComments(this.socialId);

        this.$message({
          showClose: true,
          type: "success",
          message: "撤回成功！",
        });
      })

    },

    /** 鼠标焦点*/
    inputFocus() {
      let replyInput = document.getElementById("replyInput");
      replyInput.style.padding = "8px 8px";
      replyInput.style.border = "2px solid #409EFF";
      replyInput.focus();
    },
    /** 在输入框内输入内容 */
    onDivInput(e) {
      this.replyComment = e.target.innerHTML;
    },
    /** 发表直评（非回复） */
    sendComment() {
      // 父评论（直评论）
      if (!this.replyComment) {
        this.$message({
          showClose: true,
          type: "warning",
          message: "评论不能为空",
        });
      } else {
        //將用戶的评论新增到评论表
        this.commentIf=true;//表示当前发表的是直评
       
        //查询到数据
        let a = {};
        a.avatar = this.avatar;
        a.accountName=this.accountName;
        a.content = this.replyComment;
        a.commentId=this.commentId;
        a.createTime=this.createTime;
        a.replyName = this.replyName;
        this.handlerAddComment(a);
        document.getElementById("replyInput").innerHTML = "";
        this.replyComment = "";
      }
    },

    /** 回复（追评）*/
    sendCommentReply(i) {
      // 子回复提交
      if (!this.replyComment) {
        this.$message({
          showClose: true,
          type: "warning",
          message: "评论不能为空",
        });
      } else {
        this.commentId=this.comments[i].commentId;
        //將用戶的评论新增到评论表
        this.commentIf=false;//表示当前发表的是回复
        
        // 组装请求数据
        let a = {};
        a.avatar = this.avatar;
        a.accountName=this.accountName;
        a.content = this.replyComment;
        a.commentId=this.commentId;
        a.createTime=this.createTime;
        a.replyName = this.replyName;
        this.handlerAddComment(a);

       

        this.replyComment = "";
        document.getElementsByClassName("reply-comment-input")[i].innerHTML = "";
      }
    },


    /** 回复*/
    showReplyInput(i, name, id) {
      this.comments[this.index].inputShow = false;
      this.index = i;
      this.comments[i].inputShow = true;
      this.replyName = name;
      this.replyName = "回复 @" + name;
      //alert(i)
    },
    /** 输入显示*/
    handlerInputShow(i) {
      return this.comments[i].inputShow;
    },

    /** 新增评论*/
    handlerAddComment(val){
      // this.resetCommentInfo();
      //清理空格
      if (this.replyComment) {
        this.replyComment = this.replyComment.replaceAll("&nbsp;", "")
      }
      //判断是自评还是回复
      if(this.commentIf===true){
        this.addCommentForm.content=this.replyComment;
        this.addCommentForm.socialId=this.socialId;
        this.addCommentForm.replyId=0;
      }else{
        this.addCommentForm.content=this.replyComment;
        this.addCommentForm.socialId=this.socialId;
        this.addCommentForm.replyId=this.commentId;
      }
      addComment(this.addCommentForm).then(response => {
        if (response.code == 200) {
          this.comments.push(val)
          this.$message.success("评论成功");
          //清空信息
          document.getElementById("replyInput").innerHTML = "";
          this.replyComment = "";
        }
        // this.resetCommentInfo();
        this.getComments(this.socialId);  
        
      })
    },

    /** 信息管理 按钮 */
    handlerMessage(){
      this.selectA=false;
      this.selectB=true;
      this.checkView=true;
      this.checkSysView=false;
      this.sysCheckInfo=false;
      this.resetQuery();
    },

    /** 系统信息 按钮*/
    handlerSys(){
      this.selectA=true;
      this.selectB=false;
      this.checkView=false;
      this.checkSysView=true;
      this.sysCheckInfo=false;
      this.resetQuery();
      this.getSysList();
    },



    /** 手机号脱敏 */
    phoneFormatter(phone){
      if(!equals(phone,undefined)){
        return desensitization(phone,3,7);
      }
    },
    /** 身份证号脱敏 */
    cardFormatter(card){
      if(!equals(card,undefined)){
        return desensitization(card,6,14);
      }
    },



    //查询当前物业名下的小区
    getCommunitys() {
      getCurrentList().then(response => {
        this.communityList = response.data;
      })
    },
    /** 查询信息列表 */
    getList() {
      this.loading = true;
      if (this.dateRange) { 
        this.queryParams.startTime = this.dateRange[0];
        this.queryParams.endTime=this.dateRange[1];
      }
      
      getSocialList(this.queryParams).then(response => {
          this.socialList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /**系统信息列表 querySysParams */
    getSysList() {
      this.sysLoading = true;
      getSystemList(this.addDateRange(this.querySysParams, this.dateRange)).then(response => {
          this.sysSocialList   = response.rows;
          this.sysTotal   = response.total;
          this.sysLoading = false;
        }
      );
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const socialId = row.socialId || this.ids;
      this.$confirm('是否确认删除所选中的的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delMessageSocial(socialId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },

    /** 删除按钮操作 单个删除 */
    handleDeleteOne(row) {
      const socialId = row.socialId;
      this.$confirm('是否确认删除所选中的的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delMessageSocial(socialId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },


    //删除系统信息记录  sysHandleDelete
    sysHandleDelete(row) {
      const socialId = row.socialId || this.ids;
      this.$confirm('是否确认删除所选中的的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delSystemSocial(socialId);
      }).then(() => {
        this.getSysList();
        this.$modal.msgSuccess("删除成功");
      })
    },

    //删除系统信息记录  sysHandleDelete
    sysHandleDeleteOne(row) {
      const socialId = row.socialId;
      this.$confirm('是否确认删除所选中的的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delSystemSocial(socialId);
      }).then(() => {
        this.getSysList();
        this.$modal.msgSuccess("删除成功");
      })
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.socialId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 取消按钮
    cancel() {
      if(this.selectA===false){
        this.selectA=false;
        this.selectB=true;
        this.checkView=true;
        this.checkSysView=false;
        this.sysCheckInfo=false;
      }else{
        this.selectA=true;
        this.selectB=false;
        this.checkView=false;
        this.checkSysView=true;
        this.sysCheckInfo=false;
      }
      this.checkAddUpdateView= false;
      this.sysCheckInfo=false;
      this.form.socialUrl = "";
      this.socialId="";
      this.checkPhoto = false;
      this.reset();
    },

    /** 修改按钮操作(查询相应数据讲数据铺上) */
    /* handleUpdate(row) {
       this.getCommunitys();
       this.reset();
       const socialId = row.socialId;
       getStatuteById(socialId).then(response => {
         this.checkAddUpdateView = true;
         this.form = response.data;
         this.options.socialImg= this.form.socialUrl;
         this.form.communityIds=response.commIds;
         this.title = "修改用户";
       });
     },*/

    /** 修改系统发布信息(查询相应数据讲数据铺上) */
    sysHandleUpdate(row) {
      this.getCommunitys();
      this.reset();
      const socialId = row.socialId;
      querySocialById(socialId).then(response => {
        this.checkAddUpdateView = true;
        this.form = response.data;
        this.options.socialImg= this.form.socialUrl;
        this.form.communityIds=response.commIds;
        this.title = "修改用户";
      });
    },

    /**查询详情的按钮 */
    queryInfo(row){
      this.reset();
      this.getCommunitys();
      const socialId = row.socialId;
      this.socialId=socialId;
      this.getLikeNum(socialId);
      this.getComments(socialId);
      querySocialById(socialId).then(response => {
        this.checkSysView=false;
        this.checkView=false;
        this.sysCheckInfo=true;
        this.infoTitle = "查看信息";
        this.form = response.data;
        this.form.userCommunityName = row.userCommunityName;
        this.form.userName = row.userName;
      });
    },
    //遍历小区信息communityList
    letters(commIds){
      const letters = [];
      const commList=this.communityList;
      for (let i = 0; i < commIds.length; i++) {
        for (let j = 0; j < commList.length; j++) {
          if(commIds[i]===commList[j].communityId){
            letters.push(commList[j].communityName)
          }
        }
      }

      return letters;
    },



    //查询点赞数量
    getLikeNum(socialId){
      queryLikeAmount(socialId).then(response => {
        this.likeAmount=response.likeAmount;
      });
    },

    //获取评论列表
    getComments(socialId){
      getCommentList(socialId).then(response => {
        this.comments= response.data;
        this.accountName=response.loginName;
      });
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.checkAddUpdateView= true;
      this.title = "新增";
      this.getCommunitys();
    },
    /** 添加 修改 按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.communityIds = [this.form.communityId];
          if(this.form.socialId != undefined){
            updateSocial(this.form).then(response => {
              this.$message.success("修改成功");
              this.checkSysView = true;
              this.checkAddUpdateView= false;
              this.getList();
            });
          }else{
            addSocial(this.form).then(response => {
              this.$message.success("新增成功");
              this.checkSysView = true;
              this.checkAddUpdateView= false;
              this.getList();
            });
          }
        }
      });
    },


    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 系统信息 重置按钮操作 */
    sysResetQuery() {
      this.dateRange = [];
      this.resetForm("sysQueryForm");
      this.sysHandleQuery();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 系统信息的搜索按钮的 设置 */
    sysHandleQuery() {
      this.querySysParams.pageNum = 1;
      this.getSysList();
    },
    // 表单重置
    reset() {
      this.form = {
        socialId:undefined,
        content:undefined,
        communityIds:[],
        createBy: undefined,
        userCommunityName: undefined,
      };
      this.resetForm("form");
      this.addCommentForm={
        socialId:"",
        replyId:"",
        content:undefined,
        userCommunityName:undefined
      };
    },
    resetCommentInfo() {
      this.addCommentForm={
        socialId:"",
        replyId:"",
        content:undefined,
      };
    },
    



  }
};
</script>


<style>

.my-span{
  margin-left: 3%
}

.my-input-box{
  display: flex;
  padding: 10px;
  background-color: #fafbfc;
  width: 1000px;
  margin: 0 auto;
}

.comment {
  width: 75%;
  margin: 0 auto;
  font-family: PingFang SC, HarmonyOS_Regular, Helvetica Neue, Microsoft YaHei,
  sans-serif;
  height: 300px;
  overflow-y: auto;
}
.my-reply {
  display: flex;
  padding: 10px 150px;
  background-color: #fafbfc;
}
.my-reply .header-img {
  display: inline-block;
  vertical-align: top;
}
.my-reply .reply-info {
  display: inline-block;
  margin-left: 5px;
  width: 90%;
}
@media screen and (max-width: 1200px) {
  .my-reply .reply-info {
    width: 80%;
  }
}
.my-reply .reply-info .reply-input {
  min-height: 20px;
  line-height: 22px;
  padding: 10px 10px;
  color: #ccc;
  background-color: #fff;
  border-radius: 5px;
}
.my-reply .reply-info .reply-input:empty:before {
  content: attr(placeholder);
}
.my-reply .reply-info .reply-input:focus:before {
  content: none;
}
.my-reply .reply-info .reply-input:focus {
  padding: 8px 8px;
  border: 2px solid #409eff;
  box-shadow: none;
  outline: none;
}
/* .reply-info>div .reply-input:focus{
    border: 2px solid #409EFF;
} */
.my-reply .reply-btn-box {
  height: 25px;
  margin: 10px 0;
}
.my-reply .reply-btn-box .reply-btn {
  margin-left: 10px;
  margin-top: -7px;
}
.my-comment-reply {
  margin-left: 50px;
}
.my-comment-reply .reply-input {
  width: flex;
}
.author-title:not(:last-child) {
  border-bottom: 1px solid rgba(178, 186, 194, 0.3);
}
.author-title {
  padding: 10px;
}
.author-title .header-img {
  display: inline-block;
  vertical-align: top;
}
.author-title .author-info {
  display: inline-block;
  margin-left: 5px;
  width: 60%;
  height: 40px;
  line-height: 20px;
}
.author-title .author-info > span {
  display: block;
  cursor: pointer;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.author-title .author-info .author-name {
  color: #303133;
  font-size: 18px;
  font-weight: 500;
}
.reply-box .talk-box {
  color: #606266;
}
.reply-box .talk-box span {
  color: #6298ce;
}
.author-title .author-info .author-time {
  font-size: 14px;
}
.author-time {
  color: #606266;
}
.author-title .icon-btn {
  width: 30%;
  padding: 0 !important;
  float: right;
}
@media screen and (max-width: 1200px) {
  .author-title .icon-btn {
    width: 20%;
    padding: 7px;
  }
}
.author-title .icon-btn > span {
  cursor: pointer;
}
.author-title .icon-btn .iconfont {
  margin: 0 5px;
}
.author-title .talk-box {
  margin: 0 50px;
}
.author-title .talk-box > p {
  margin: 0;
}
.author-title .talk-box .reply {
  font-size: 16px;
  color: #606266;
}

.author-title .reply-box {
  margin: 10px 0 0 50px;
  background-color: #efefef;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.clearInfo{
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

</style>
