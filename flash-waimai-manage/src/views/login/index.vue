<template>
  <!--  <div class="login-container"  style=" background-image: url(static/img/bg.jpg);">-->
  <div class="login-container" style=" background: linear-gradient(135deg, rgb(57, 167, 176), rgb(56, 183, 145));">

    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
             label-position="left">

      <div class="title-container">
        <h3 class="title">{{ $t('login.title') }}</h3>
        <lang-select class="set-language"/>
      </div>
      <div style="margin-bottom:20px;text-align: center;">
        <el-radio style="color: #FFFFFF" v-model="loginForm.userType" label="1">我是管理员</el-radio>
        <el-radio style="color: #FFFFFF" v-model="loginForm.userType" label="2">我是商户</el-radio>
      </div>
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user"/>
        </span>
        <el-input
          v-model="loginForm.username"
          :placeholder="$t('login.username')"
          name="username"
          type="text"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password"/>
        </span>
        <el-input
          :type="pwdType"
          v-model="loginForm.password"
          :placeholder="$t('login.password')"
          name="password"
          auto-complete="on"
          @keyup.enter.native="handleLogin"/>
        <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye"/>
        </span>
      </el-form-item>

      <!--      <el-button :loading="loading" type="warning" style="width:100%;margin-bottom:30px;"-->
      <!--                 @click.native.prevent="handleLogin">{{ $t('login.logIn') }}-->
      <!--      </el-button>-->
      <el-button :loading="loading" type="info" class="edit"
                 @click.native.prevent="handleLogin">{{ $t('login.logIn') }}
      </el-button>

      <div style="position:relative">
        <div class="tips">
          <span>{{ $t('login.username') }} : admin</span>
          <span>{{ $t('login.password') }} : admin</span>
        </div>
        <div class="tips">
          <span style="margin-right:18px;">{{ $t('login.username') }} : developer</span>
          <span>{{ $t('login.password') }} : developer</span>
        </div>
        <div class="tips">
          <span>商铺账号 : 各个商铺自己的名称</span>
          <span>{{ $t('login.password') }} : 123456</span>
        </div>
      </div>
    </el-form>


  </div>
</template>

<script src="./login.js"></script>
<style rel="stylesheet/scss" lang="scss" src="./login.scss"></style>


<style>
.edit {
  color: #fff;
  background-color: rgb(21, 47, 72);
  border-color: rgb(21, 47, 72);
  width:100%;margin-bottom:30px;
}

.edit:hover,
.edit:focus {
  background: var(--el-button-hover-color);
  border-color: var(--el-button-hover-color);
  color: var(--el-button-font-color);
}

</style>
