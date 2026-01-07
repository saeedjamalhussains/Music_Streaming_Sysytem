Docker Desktop:
Open Docker Desktop -> go to Settings -> Kubernetes -> turn on “Enable Kubernetes”.

Install Ansible and dependencies inside WSL Ubuntu:

Open Ubuntu (WSL).

Run these commands:
sudo apt update
sudo apt install -y ansible python3-pip python3-venv
python3 -m venv ~/ansible-env
source ~/ansible-env/bin/activate
pip install kubernetes
ansible-galaxy collection install kubernetes.core

Navigate to your project inside WSL:
cd /mnt/e/devsprint7/cicdproject

Build Docker images for Kubernetes:
docker build -t cicdproject-backend:latest ./backend
docker build -t cicdproject-frontend:latest ./frontend

Run the Ansible playbook (this applies the Kubernetes yaml files):
cd ansible
ansible-playbook -i inventory.ini deploy_k8s.yml

Check Kubernetes pod status:
kubectl get pods
kubectl get services

To access the project in browser:
Frontend: http://localhost:3001

Backend API: http://localhost:5050

To stop your Kubernetes deployment:
kubectl delete -f ./k8s