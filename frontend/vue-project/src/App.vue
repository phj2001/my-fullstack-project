<script setup>
import { ref } from 'vue'

// 加载状态，用于控制按钮的禁用状态和显示加载动画
const isLoading = ref(false)

// 响应式变量，存储从后端获取的消息
// 初始消息更具引导性
const message = ref('准备就绪，等待您的指令')

// 异步方法，用于请求后端API
async function fetchMessage() {
  // 开始加载，设置加载状态为true
  isLoading.value = true
  // 先清空旧消息，准备迎接新消息
  message.value = '正在穿越星际网络获取数据...'

  try {
    // 模拟网络延迟，让加载动画更明显（真实项目中可移除）
    await new Promise(resolve => setTimeout(resolve, 1500));

    // 使用fetch API发送网络请求
    const response = await fetch('http://localhost:8080/api/hello')

    // 检查响应是否成功
    if (!response.ok) {
      throw new Error(`网络响应错误: ${response.statusText}`);
    }

    const backendMessage = await response.text()
    
    // 更新 message 的值
    message.value = backendMessage
  } catch (error) {
    // 请求失败时，在控制台打印错误，并更新消息
    console.error('请求后端数据失败:', error)
    message.value = '获取数据失败！请检查后端服务或网络连接。'
  } finally {
    // 无论成功还是失败，最终都结束加载状态
    isLoading.value = false
  }
}
</script>

<template>
  <div class="background-container">
    <div class="glass-card">
      <header class="card-header">
        <h1>未来通讯模块</h1>
        <p class="subtitle">一个连接前端与后端的实验性接口</p>
      </header>

      <main class="card-body">
        <!-- 
          使用 Vue 的 <Transition> 组件为消息的出现和消失添加动画。
          `mode="out-in"` 确保旧元素先离开，新元素再进入，效果更平滑。
        -->
        <Transition name="fade" mode="out-in">
          <p class="message-display">
            <!-- 使用一个 key 来强制重新渲染 p 元素，从而触发过渡效果 -->
            <span :key="message">{{ message }}</span>
          </p>
        </Transition>
      </main>

      <footer class="card-footer">
        <!-- 
          当 isLoading 为 true 时，禁用按钮并显示加载状态。
          :disabled 属性绑定了 isLoading 的值。
        -->
        <button @click="fetchMessage" :disabled="isLoading" class="action-button">
          <span v-if="isLoading" class="loader"></span>
          <span v-else>连接后端获取密文</span>
        </button>
      </footer>
    </div>
  </div>
</template>

<style scoped>
/* 引入一个优雅的字体，如果网络允许，可以从Google Fonts引入 */
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap');

/* --- 1. 动态渐变背景 --- */
.background-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  font-family: 'Poppins', sans-serif;
  background: linear-gradient(45deg, #fbc2eb, #a6c1ee, #84fab0, #8fd3f4);
  background-size: 400% 400%;
  animation: gradient-animation 15s ease infinite;
  overflow: hidden; /* 防止卡片动画溢出 */
}

@keyframes gradient-animation {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}

/* --- 2. 玻璃拟态卡片 --- */
.glass-card {
  width: 90%;
  max-width: 600px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.15);
  border-radius: 20px;
  border: 1px solid rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(15px);
  -webkit-backdrop-filter: blur(15px); /* 兼容 Safari */
  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
  color: white;
  text-align: center;
  display: flex;
  flex-direction: column;
  gap: 2rem;
  
  /* 卡片入场动画 */
  animation: card-fade-in 1s cubic-bezier(0.25, 0.46, 0.45, 0.94) both;
}

@keyframes card-fade-in {
  from {
    opacity: 0;
    transform: translateY(30px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

/* --- 3. 内容与排版 --- */
.card-header h1 {
  font-size: 2.5rem;
  font-weight: 600;
  margin: 0;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.subtitle {
  font-size: 1rem;
  font-weight: 300;
  opacity: 0.8;
  margin-top: 8px;
}

.message-display {
  min-height: 60px; /* 保证高度稳定，防止文字变化时布局跳动 */
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  background-color: rgba(0, 0, 0, 0.2);
  border-radius: 10px;
  font-size: 1.1rem;
  line-height: 1.6;
  font-weight: 400;
  transition: background-color 0.3s ease;
}

/* --- 4. 交互式按钮与加载动画 --- */
.action-button {
  background: rgba(255, 255, 255, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-radius: 50px;
  padding: 15px 30px;
  font-size: 1rem;
  font-weight: 600;
  color: white;
  cursor: pointer;
  outline: none;
  transition: all 0.3s ease;
  position: relative;
}

.action-button:hover:not(:disabled) {
  background: rgba(255, 255, 255, 0.3);
  box-shadow: 0 0 15px rgba(255, 255, 255, 0.5);
  transform: translateY(-2px);
}

.action-button:active:not(:disabled) {
  transform: translateY(0);
}

.action-button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

/* 加载动画 (纯CSS实现) */
.loader {
  width: 20px;
  height: 20px;
  border: 3px solid rgba(255, 255, 255, 0.3);
  border-top-color: #fff;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  display: inline-block;
  vertical-align: middle;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

/* --- 5. Vue Transition 动画 --- */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease, transform 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(10px);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .glass-card {
    padding: 25px;
  }
  .card-header h1 {
    font-size: 2rem;
  }
}
</style>