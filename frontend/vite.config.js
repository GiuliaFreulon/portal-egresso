import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    strictPort: true,
    allowedHosts: ['a9bf-2804-d4b-8300-a000-1ab-857f-5c8a-b185.ngrok-free.app']
  },
});
