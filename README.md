# Poshmark_ResourceAllocator
Optimal CPU resource allocator based on the user request.

ResourceAllocator project is spring boot based RESTFul web service, which provide optimal cpu resouce allocation either on min_cpu_request 
or max_price_request or combintion of both. For this it gets Cpu configuration details and Price details of available regions 
stored in embedded in_memeory H2 database as entity, which along with the request details is used to make allocation.

For allocation, "Unbounded Knapsack" algorithm with bottom up meoization technique(solving subproblem and using that result for parent problems) 
is used to provide scalable solution for min_cpu_request. Recursive approach is used for price based cpu request, because of the continous nature
of the price value. Optmization algorithm performance is improved by providing "Multi threading" ability to methods. 

"Facade Design pattern" is employed on OptmizerFacade class, to remove complexity on selection resource allocator based on request. Source codes
are written to comply with SOLID design principles.

Sample JSON respone of this webservice is available in the Resource_Allocator_Response folder.


Sample Input 

Step 1

  Updating server cpu details
  
  URI - http://localhost:8080/CpuDets
  
  METHOD - POST

  JSON Payload -
  {
    "large": 1,
    "xlarge": 2,
    "2xlarge": 4,
    "4xlarge": 8,
    "8xlarge": 16,
    "10xlarge": 32
  }
  
  Response JSON -
  
  {
    "large": 1,
    "xlarge": 2,
    "2xlarge": 4,
    "8xlarge": 16,
    "10xlarge": 32,
    "4xlarge": 8
}
  
  
Step 2 


  Updating server cpu details
  
  URI - http://localhost:8080/ResourceInfo/Resource
  
  METHOD - POST

  JSON payload
{
    "us-west": {
        "large": 0.12,
        "xlarge": 0.23,
        "2xlarge": 0.45,
        "4xlarge": 0.774,
        "8xlarge": 1.4,
        "10xlarge": 2.82
     },

    "us-east": {
        "large":0.14,
        "2xlarge":0.413,
        "4xlarge":0.89,
        "8xlarge":1.3,
        "10xlarge":2.97
    } 
}
  
  Response JSON -
  
{
    "us-west": {
        "large": 0.12,
        "xlarge": 0.23,
        "2xlarge": 0.45,
        "4xlarge": 0.774,
        "8xlarge": 1.4,
        "10xlarge": 2.82
     },

    "us-east": {
        "large":0.14,
        "2xlarge":0.413,
        "4xlarge":0.89,
        "8xlarge":1.3,
        "10xlarge":2.97
    } 
}
 
 Step 2 


  Updating server cpu details
  
  URI - http://localhost:8080/ServerCost/hours/{24}/cpus/{70}/price/{150}
  
  METHOD - GET
  
  Response JSON -
  

    {
        "total_cpu": 70,
        "server": {
            "2xlarge": 1,
            "8xlarge": 4,
            "large": 2
        },
        "total_price": 141.43199,
        "region": "us-east"
    },
    {
        "total_cpu": 0,
        "server": "no server available",
        "total_price": 150.0,
        "region": "us-west"
    }
