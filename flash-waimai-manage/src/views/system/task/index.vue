<template>
  <div class="app-container">
    <div class="block">
      <el-row  :gutter="20">
        <el-col :span="6">
          <el-input v-model="listQuery.name" placeholder="请输入任务名"></el-input>
        </el-col>
        <el-col :span="6">
          <el-button type="success" icon="el-icon-search" @click.native="search">{{ $t('button.search') }}</el-button>
          <el-button type="primary" icon="el-icon-refresh" @click.native="reset">{{ $t('button.reset') }}</el-button>
        </el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :span="24">
          <el-button type="success" icon="el-icon-plus" @click.native="add">{{ $t('button.add') }}</el-button>
          <el-button type="primary" icon="el-icon-edit" @click.native="edit">{{ $t('button.edit') }}</el-button>
          <el-button type="danger" icon="el-icon-delete" @click.native="remove">{{ $t('button.delete') }}</el-button>
        </el-col>
      </el-row>
    </div>


    <el-table :data="list" v-loading="listLoading" element-loading-text="Loading" border fit highlight-current-row
    @current-change="handleCurrentChange">
      <el-table-column label="任务名">
        <template slot-scope="scope">
          {{scope.row.name}}
        </template>
      </el-table-column>
      <el-table-column label="执行类" width="300">
        <template slot-scope="scope">
          {{scope.row.jobClass}}
        </template>
      </el-table-column>
      <el-table-column label="定时规则">
        <template slot-scope="scope">
          {{scope.row.cron}}
        </template>
      </el-table-column>

      <el-table-column label="说明">
        <template slot-scope="scope">
          {{scope.row.note}}
        </template>
      </el-table-column>

      <el-table-column label="最近执行时间">
        <template slot-scope="scope">
          {{scope.row.execAt}}
        </template>
      </el-table-column>

      <el-table-column label="最近执行结果">
        <template slot-scope="scope">
          {{scope.row.execResult}}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
        <el-button icon="el-icon-log" size="mini" @click.native="viewLog(scope.row.id)">查看日志</el-button>
          <el-button type="success" icon="el-icon-log" size="mini" @click.native="enable(scope.row.id)"
                     v-if="scope.row.disabled===true">启用</el-button>
          <el-button type="danger" icon="el-icon-log" size="mini" @click.native="disable(scope.row.id)"
                     v-if="scope.row.disabled===false">禁用</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :title="formTitle"
      :visible.sync="formVisible"
      width="70%">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="任务名" prop="name">
              <el-input v-model="form.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="定时规则" prop="cron">
              <el-input v-model="form.cron"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item label="执行类" prop="jobClass">
              <el-input v-model="form.jobClass" type="textarea"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务说明" prop="cfgDesc">
              <el-input v-model="form.note" type="textarea"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="执行参数">
              <el-input v-model="form.data" type="textarea"></el-input>
            </el-form-item>
          </el-col>

        </el-row>
        <el-form-item>
          <el-button type="primary" @click="save">{{ $t('button.submit') }}</el-button>
          <el-button @click.native="formVisible = false">{{ $t('button.cancel') }}</el-button>
        </el-form-item>

      </el-form>
    </el-dialog>
  </div>
</template>

<script src="./task.js"></script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/common.scss";
</style>

