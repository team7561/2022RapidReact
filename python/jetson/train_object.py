import torch
import torchvision
import threading
import time
from utils import preprocess
import torch.nn.functional as F
import traitlets
import torchvision.transforms as transforms


device = torch.device('cuda')
TASK = 'balls'
CATEGORIES = ['red_ball',' blue_ball']
DATASETS = ['A']

TRANSFORMS = transforms.Compose([
    transforms.ColorJitter(0.2, 0.2, 0.2, 0.2),
    transforms.Resize((224, 224)),
    transforms.ToTensor(),
    transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])
])



datasets = {}
dataset = datasets[DATASETS[0]]
path = ""


device = torch.device('cuda')
output_dim = 2 * len(dataset.categories)  # x, y coordinate for each category

# ALEXNET
# model = torchvision.models.alexnet(pretrained=True)
# model.classifier[-1] = torch.nn.Linear(4096, output_dim)

# SQUEEZENET 
# model = torchvision.models.squeezenet1_1(pretrained=True)
# model.classifier[1] = torch.nn.Conv2d(512, output_dim, kernel_size=1)
# model.num_classes = len(dataset.categories)

# RESNET 18
model = torchvision.models.resnet18(pretrained=True)
model.fc = torch.nn.Linear(512, output_dim)

# RESNET 34
# model = torchvision.models.resnet34(pretrained=True)
# model.fc = torch.nn.Linear(512, output_dim)

model = model.to(device)

def load_model(c):
    model.load_state_dict(torch.load(path))
    
def save_model(c):
    torch.save(model.state_dict(), path)


# display(model_widget)
print("model configured and model_widget created")