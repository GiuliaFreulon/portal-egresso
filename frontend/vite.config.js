import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    strictPort: true,
    allowedHosts: ['03ee-2804-d4b-8300-a000-70a6-5552-e13a-598a.ngrok-free.app']
  },
});
