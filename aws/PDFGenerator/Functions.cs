using System.Net;
using Amazon.Lambda.Core;
using Amazon.Lambda.APIGatewayEvents;
using Syncfusion.Pdf;
using Syncfusion.Pdf.Graphics;
using Syncfusion.Drawing;
using Amazon.S3;
using Amazon.S3.Transfer;
using Newtonsoft.Json;

// Assembly attribute to enable the Lambda function's JSON input to be converted into a .NET class.
[assembly: LambdaSerializer(typeof(Amazon.Lambda.Serialization.SystemTextJson.DefaultLambdaJsonSerializer))]

namespace PDFGenerator;

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
    public APIGatewayProxyResponse Post(APIGatewayProxyRequest request, ILambdaContext context)
    {
        context.Logger.LogInformation("Get Request\n");
        var stringRequest = request.Body.ToString();

        var content = JsonConvert.DeserializeObject<PostRequest>(stringRequest);

        PdfDocument pdfDocument = new ();
        PdfPage page = pdfDocument.Pages.Add();
        PdfGraphics graphics = page.Graphics;
        PdfFont font = new PdfStandardFont(PdfFontFamily.Helvetica, 15);
        graphics.DrawString("Comprobante de pago", font, PdfBrushes.Black, new PointF(20, 0));
        graphics.DrawString("Transacción: " + content.Transaction, font, PdfBrushes.Black, new PointF(20, 20));
        graphics.DrawString("Cuenta Origen: "+ content.Origin, font, PdfBrushes.Black, new PointF(20, 40));
        graphics.DrawString("Cuenta Destino: " + content.Destination, font, PdfBrushes.Black, new PointF(20, 60));
        graphics.DrawString("Monto transferencia: " + content.Amount, font, PdfBrushes.Black, new PointF(20, 80));


        MemoryStream stream = new MemoryStream();
        pdfDocument.Save(stream);
        pdfDocument.Close(true);

        var formatted = content.Transaction + ".pdf";
        var res = memUploadFile(stream, formatted);

        var response = new APIGatewayProxyResponse
        {
            StatusCode = (int)HttpStatusCode.Created,
            Body = res.ToString(),
            Headers = new Dictionary<string, string> { { "Content-Type", "text/plain" } }
        };

        return response;
    }

    private static bool memUploadFile(MemoryStream memFile, string path)
    {
        try
        {
            var client = new AmazonS3Client();
            using(TransferUtility transferUtility = new TransferUtility(client))
            {
                transferUtility.Upload(memFile, "vouchers-mofp", path);

                return true;
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine(ex.Message);
            return false;
        }
    }
}