<script setup>
import { ref } from 'vue'

// 创建一个响应式变量来存储从后端获取的消息
// ref() 是Vue 3的组合式API，用来创建响应式数据。
// 当 message.value 的值改变时，模板中用到它的地方会自动更新。
const message = ref('点击按钮从后端获取数据')

// 定义一个方法，用于请求后端API
async function fetchMessage() {
  try {
    // 使用浏览器内置的 fetch API 发送网络请求
    // 注意：这里的 URL 必须是后端服务的完整地址
    const response = await fetch('http://localhost:8080/api/hello')

    // 将返回的响应体解析为文本
    const backendMessage = await response.text()

    // 更新 message 的值
    message.value = backendMessage
  } catch (error) {
    // 如果请求失败，在控制台打印错误，并更新消息
    console.error('请求后端数据失败:', error)
    message.value = '获取数据失败，请检查后端服务是否开启！'
  }
}
</script>

<template>
  <div class="container">
    <h1>我的第一个全栈应用</h1>
    <!-- 
      v-on:click 或者简写 @click 是Vue的事件绑定指令。
      当用户点击这个按钮时，它会调用 <script> 部分定义的 fetchMessage 方法。
    -->
    <button @click="fetchMessage">向后端请求数据</button>
    
    <!-- 
      双大括号 {{ }} 是Vue的文本插值语法。
      它会把 message 变量的当前值显示在这里。
    -->
    <p class="message-display">来自后端的消息: <strong>{{ message }}</strong></p>
  </div>
</template>

<style scoped>
.container {
  max-width: 600px;
  margin: 50px auto;
  text-align: center;
  font-family: sans-serif;
}
button {
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  margin-bottom: 20px;
}
.message-display {
  padding: 20px;
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  border-radius: 5px;
}
</style>