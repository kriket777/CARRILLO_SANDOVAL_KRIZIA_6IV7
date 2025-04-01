import pandas as pd
import matplotlib.pyplot as plt
#ruta
df = pd.read_excel(r'C:\Users\HP\Desktop\CARRILLO_SANDOVAL_KRIZIA_6IV7\AnalisisDeDatos\PrimerosPasos\proyecto1.xlsx')

#print(df.head())
#print(df.tail())

VentasTotales = df["ventas_tot"].sum()
print('1.- \nLas ventas totales son: ', VentasTotales)


filtrocon = df[df["B_adeudo"] == "Con adeudo"]["no_clientes"].sum()
filtrosin = df[df["B_adeudo"] == "Sin adeudo"]["no_clientes"].sum()
TotalSocios = filtrocon + filtrosin
PorcentajeCon= (filtrocon/TotalSocios)*100
PorcentajeSin= (filtrosin/TotalSocios)*100
print(f"\n2.- \nCantidad socios sin adeudo: {filtrocon} ({PorcentajeCon:.3f})%")
print(f"Socios sin adeudo: {filtrosin} ({PorcentajeSin:.3f}%)\n")





df_fecha_ventas = df.groupby("B_mes")["ventas_tot"].sum()
#hiposis correcta; parece q el .groupby tiene algo que ver
#print(f"ventas por mes creo {df_fecha_ventas}")
plt.figure(figsize=(10, 6))
plt.bar(df_fecha_ventas.index, df_fecha_ventas.values, color='pink', width=10)  # Ajusta width para cambiar el grosor
plt.xlabel('Meses')
plt.ylabel('Ventas Totales')
plt.title('Ventas Totales por Mes')
# Rotar las etiquetas del eje X para que se vean bien
plt.xticks(rotation=45)
plt.tight_layout()
plt.show()



df_std_pagos = df.groupby("B_mes")["pagos_tot"].std()
plt.figure(figsize=(10, 5))
plt.bar(df_std_pagos.index,df_std_pagos.values, color='purple', width=10)
promedio = df['pagos_tot'].mean()
plt.axhline(promedio, color='black', linestyle='--', linewidth=2, label="Promedio de los pagos totales")
plt.xlabel("Fecha")
plt.ylabel("Pagos")
plt.title("Desviación Estándar de los Pagos por Mes")
plt.show()

DeudaTotal = df["adeudo_actual"].sum()
print(f"5.-\nLa deduda total es:{DeudaTotal:.3f}")



PorcentajeUtilidad=((VentasTotales-DeudaTotal)/VentasTotales)*100
print(f"6.-\nEl porcentaje de utilidad es  {PorcentajeUtilidad:.2f}%")

# Agrupar las ventas por entidad (sucursal)
VentasSucursal = df.groupby("id_sucursal")["ventas_tot"].sum()
plt.figure(figsize=(8, 8))
plt.pie(VentasSucursal, labels=VentasSucursal.index, autopct="%1.1f%%", colors=["#58d68d", "#7dcea0", "#f9e79f", "#fcf3cf", "#f5cba7"], startangle=140)
plt.title("Distribución de Ventas por Sucursal")
plt.show()


#8. Deudas de sucursal VS margen de utilidad
df_utilidad_sucursal = df.groupby("id_sucursal").agg({"ventas_tot": "sum", "adeudo_actual": "sum"})
df_utilidad_sucursal["margen_utilidad"] = df_utilidad_sucursal["ventas_tot"] - df_utilidad_sucursal["adeudo_actual"]

fig, ax = plt.subplots(figsize=(10, 5))
ax.bar(df_utilidad_sucursal.index, df_utilidad_sucursal["adeudo_actual"], label="Deuda Total", color="red")
ax.bar(df_utilidad_sucursal.index, df_utilidad_sucursal["margen_utilidad"], label="Margen de Utilidad", color="green", bottom=df_utilidad_sucursal["adeudo_actual"][:30])
ax.set_xlabel("Sucursal")
ax.set_ylabel("Monto")
ax.set_title("Deuda Total vs Margen de Utilidad por Sucursal")
ax.legend()
plt.show()