using System.Net;
using Amazon.Lambda.Core;
using Amazon.Lambda.APIGatewayEvents;
using Amazon.DynamoDBv2;
using Amazon.DynamoDBv2.Model;
using Newtonsoft.Json;
using Amazon.Runtime.Internal.Transform;

// Assembly attribute to enable the Lambda function's JSON input to be converted into a .NET class.
[assembly: LambdaSerializer(typeof(Amazon.Lambda.Serialization.SystemTextJson.DefaultLambdaJsonSerializer))]

namespace Currency;

public class Functions
{
    /// <summary>
    /// Default constructor that Lambda will invoke.
    /// </summary>
    public Functions()
    {
    }

    /// <summary>
    /// A Lambda function to respond to HTTP Get methods from API Gateway
    /// </summary>
    /// <param name="request"></param>
    /// <returns>The API Gateway response.</returns>
    public async Task<APIGatewayProxyResponse> Get(APIGatewayProxyRequest request, ILambdaContext context)
    {
        context.Logger.LogInformation("Get Request\n");

        var response = new APIGatewayProxyResponse
        {
            StatusCode = (int)HttpStatusCode.OK,
            Body = await ScanReadingList(),
            Headers = new Dictionary<string, string> { { "Content-Type", "text/json" }, { "Access-Control-Allow-Origin", "*"} }
        };

        return response;
    }

    private async Task<string> ScanReadingList()
    {
        var client = new AmazonDynamoDBClient();

        var request = new ScanRequest
        {
            TableName = "Currencies"
        };

        var response = await client.ScanAsync(request);

        List<Currency> currencyList = new List<Currency>();

        foreach (Dictionary<string, AttributeValue> item in response.Items)
        {
            Dictionary<string, AttributeValue> currency = item;
            currencyList.Add(new Currency
            {
                Id = currency["id"].S,
                Name = currency["name"].S
            });
        }

        return JsonConvert.SerializeObject(currencyList);
    }
}