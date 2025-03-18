import tensorflow as tf
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
import pandas as pd


data = pd.DataFrame({
    'connections': [5, 2, 10, 1, 7],
    'transactions': [20, 5, 15, 2, 10],
    'balance': [1000, 500, 2000, 300, 1500],
    'churn': [0, 1, 0, 1, 0]
})

X = data[['connections', 'transactions', 'balance']]
y = data['churn']

scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

X_train, X_test, y_train, y_test = train_test_split(X_scaled, y, test_size=0.2, random_state=42)

model = tf.keras.Sequential([
    tf.keras.layers.Input(shape=(3,)), 
    tf.keras.layers.Dense(64, activation='relu'),
    tf.keras.layers.Dense(32, activation='relu'),
    tf.keras.layers.Dense(1, activation='sigmoid')
])

model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

# Entraînement
model.fit(X_train, y_train, validation_split=0.2, epochs=20, batch_size=32)

model.save("churn_model.h5")
import joblib
joblib.dump(scaler, "scaler.pkl")

print("Modèle et scaler sauvegardés.")
