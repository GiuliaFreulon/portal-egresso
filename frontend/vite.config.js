import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    strictPort: true,
    allowedHosts: ['d1ad-45-190-120-66.ngrok-free.app', 'rates-along-participated-tcp.trycloudflare.com']
  },
});
