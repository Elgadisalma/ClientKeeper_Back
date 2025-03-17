FROM python:3.10-slim

WORKDIR /app

# Copie des fichiers nécessaires
COPY requirements.txt /app/
COPY train_model.py /app/
COPY app.py /app/

# Installation des dépendances
RUN pip install --no-cache-dir -r requirements.txt

# Exécution du script d'entraînement pour créer le modèle
RUN python train_model.py

EXPOSE 5000

# Démarrage de l'application Flask
CMD ["python", "app.py"]