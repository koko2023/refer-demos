<template>
  <div class="chatroom-container">
    <div class="messages">
      <div v-for="(message, index) in messages" :key="index" class="message">
        {{ message }}
      </div>
    </div>
    <input v-model="inputValue" @keyup.enter="sendMessage" placeholder="Type a message..." />
    <button @click="sendMessage">Send</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      inputValue: '',
      messages: []
    };
  },
  methods: {
    async sendMessage() {
      const query = this.inputValue;
      this.messages.push(`You: ${query}`);
      this.inputValue = '';
      
      try {
        const response = await axios.post('/llm', {
          inputs: {},
          query: query,
          response_mode: 'streaming',
          conversation_id: '',
          user: 'abc-123',
          files: [
            {
              type: 'image',
              transfer_method: 'remote_url',
              url: 'https://cloud.dify.ai/logo/logo-site.png'
            }
          ]
        }, {
          headers: {
            'Authorization': `Bearer ${process.env.VUE_APP_API_KEY}`,
            'Content-Type': 'application/json'
          }
        });

        if (response.status === 200) {
          const answer = response.data.answer;
          this.messages.push(`Bot: ${answer}`);
        } else {
          this.messages.push(`Error: ${response.status}`);
        }
      } catch (error) {
        console.error('API Request Failed:', error);
        this.messages.push('Failed to fetch data');
      }
    }
  }
};
</script>

<style scoped>
.chatroom-container {
  padding: 10px;
}
.messages {
  height: 300px;
  overflow-y: auto;
  border: 1px solid #ccc;
  margin-bottom: 10px;
  padding: 10px;
}
.message {
  margin: 5px 0;
}
input {
  width: calc(100% - 80px);
  padding: 10px;
  margin-right: 10px;
}
button {
  padding: 10px 20px;
}
</style>
