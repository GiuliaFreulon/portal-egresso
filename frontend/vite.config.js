import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000, // ou qualquer outra porta que preferir
    strictPort: true,
    allowedHosts: ['2958-45-190-120-75.ngrok-free.app'] // Adicione o host aqui5173
  },
});
