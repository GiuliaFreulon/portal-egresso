import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    strictPort: true,
    allowedHosts: ['2437-2804-d4b-8300-a000-a049-a172-3781-4857.ngrok-free.app']
  },
});
