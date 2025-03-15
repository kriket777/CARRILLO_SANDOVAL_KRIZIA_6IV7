import pandas as pd
import matplotlib.pyplot as plt

# Cargar archivo
df = pd.read_csv('./ANALISISDEDATOS/housing.csv')


# Calcular estadísticas descriptivas
datos = df['median_house_value']
resumen = datos.agg(['mean', 'median', lambda x: x.mode()[0], 'max', 'min', 'var', 'std'])
resumen.index = ['Media', 'Mediana', 'Moda', 'Máximo', 'Mínimo', 'Varianza', 'Desviación Estándar']
resumen.loc['Rango'] = resumen['Máximo'] - resumen['Mínimo']

# Mostrar resumen estadístico
print(resumen.to_frame().T, '\n', datos.value_counts().head(10))

# Gráfico de barras comparativo
plt.figure(figsize=(10, 5))
plt.bar(df.index[:20], df['median_house_value'][:20], label='median_house_value', color='b', alpha=0.7)
plt.bar(df.index[:20], df['population'][:20], label='population', color='r', alpha=0.5)
plt.axhline(datos.mean(), color='g', linestyle='dashed', linewidth=2, label='Promedio')

# Personalización del gráfico
plt.xlabel('Índice')
plt.ylabel('Valores')
plt.title('Comparación de median_house_value y population')
plt.legend()
plt.grid(axis='y', linestyle='--', alpha=0.7)
plt.show()