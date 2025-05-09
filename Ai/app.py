from flask import Flask, request, jsonify
import tensorflow as tf
import numpy as np  # pour transformer les données d'entrée
import joblib #charger le scaler

app = Flask(__name__)

model = tf.keras.models.load_model("churn_model.h5")
scaler = joblib.load("scaler.pkl")

@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.get_json()
        users = data['users'] 
        
        features = np.array([[user['connections'], user['transactions'], user['balance']] for user in users])
        
        features_scaled = scaler.transform(features)
        
        predictions = model.predict(features_scaled).flatten()
        
        results = []
        for user, pred in zip(users, predictions):
            churn_score = 0  
            if user['transactions'] > 3 and user['connections'] > 7:
                churn_score = 0  # Fidele
            elif 1 <= user['transactions'] <= 2 and 3 <= user['connections'] <= 6:
                churn_score = 1  # Risque moyen
            elif user['transactions'] == 0 and user['connections'] <= 2:
                churn_score = 2  # Risque eleve
            
            results.append({
                "id": user['id'],
                "churn_score": churn_score
            })
        
        return jsonify(results)
    except Exception as e:
        return jsonify({"error": str(e)}), 400

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
