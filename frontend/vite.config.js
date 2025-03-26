import { defineConfig, loadEnv } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig(({ mode }) => {
  // Carrega variáveis do ambiente começando com VITE_
  const env = loadEnv(mode, process.cwd(), 'VITE_');

  return {
    plugins: [
      react({
        // Habilita o Fast Refresh e JSX runtime automático
        jsxRuntime: 'automatic',
        // Adicione se estiver usando Emotion ou styled-components
        babel: {
          plugins: ['@emotion/babel-plugin'],
        },
      }),
    ],
    server: {
      proxy: {
        '/api': {
          target: env.VITE_API_URL || 'http://localhost:8080',
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, ''),
          // Para HTTPS no Railway
          secure: process.env.NODE_ENV === 'production',
        }
      },
      host: true,       // Expõe para rede local
      port: 3000,       // Porta fixa
      strictPort: true, // Fecha se porta estiver ocupada
      open: true        // Abre navegador automaticamente
    },
    build: {
      sourcemap: true,  // Gera source maps para debug
      outDir: 'dist',   // Diretório de build
      assetsDir: 'assets' // Organização de arquivos estáticos
    },
    resolve: {
      alias: {
        // Configura aliases se necessário
        '@': '/src',
      }
    }
  };
});