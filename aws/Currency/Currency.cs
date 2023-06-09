﻿namespace Currency;

using Amazon.DynamoDBv2.DataModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

public class Currency
{
    [DynamoDBHashKey]
    public string Id { get; set; }
    [DynamoDBRangeKey]
    public string Name { get; set; }
}
